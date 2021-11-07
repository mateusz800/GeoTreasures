package com.example.geotreasures.map

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.geotreasures.MainApplication
import com.example.geotreasures.R
import com.example.geotreasures.Singletons
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.network.DataFetcher
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GoogleMapViewController :
    MapViewController(), GoogleMap.OnCameraIdleListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMapClickListener {

    private val context = MainApplication.applicationContext()
    private var mapObject: GoogleMap? = null
    private var selectedMarker:Marker? = null
    private var currentLocationMarker:Marker? = null
    private val cacheMarkers:MutableSet<Marker> = mutableSetOf()

    init {
        Singletons.mapController = this
    }

    @Composable
    fun createMapView(): MapView {
        val context = LocalContext.current
        val map = remember {
            MapView(context).apply {
                id = R.id.map
            }
        }
        map.onCreate(Bundle());
        return map
    }

    @SuppressLint("MissingPermission")
    fun setMapObject(mapObject: GoogleMap) {
        this.mapObject = mapObject
        if(!checkPermissions()){
            mapObject.isMyLocationEnabled = false
            mapObject.uiSettings.isMyLocationButtonEnabled = false
            return
        } else {
            mapObject.isMyLocationEnabled = true
            mapObject.uiSettings.isMyLocationButtonEnabled = true
            mapObject.setOnMyLocationButtonClickListener(this)
        }
        mapObject.setOnCameraIdleListener(this)
        mapObject.setOnMarkerClickListener(this)
        mapObject.setOnMapClickListener(this)
    }



    @SuppressLint("MissingPermission")
    override fun centerOnCurrentLocation() {
        if(!checkPermissions()){
            // TODO: handle no permission
            return
        }
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                mapObject?.animateCamera(
                    CameraUpdateFactory.newLatLng(latLng)
                )
                if(currentLocationMarker == null){
                    currentLocationMarker = addMarker(latLng, MapMarker.CURRENT_LOCATION.bitmap)
                } else {
                    currentLocationMarker?.position = latLng
                }
            }
    }



    override fun onCameraIdle() {
        val location = mapObject?.cameraPosition?.target
        MainScope().launch {
            if (location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                val caches =
                    DataFetcher.fetchNearlyCaches(latLng)
                selectedMarker = null

                val itr = cacheMarkers.iterator()
                while (itr.hasNext()) {
                    val marker = itr.next()
                    if(marker != selectedMarker){
                        marker.remove()
                        itr.remove()
                    }
                }

                caches.forEach { model ->
                    val marker = addMarker(model.location, MapMarker.DEFAULT.bitmap, model)
                    if(marker != null){
                        cacheMarkers.add(marker)
                    }
                }
            }
        }
    }
    override fun addMarker(position: LatLng, marker: Bitmap): Marker? {
       return mapObject?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(marker))
                .draggable(false)
                .visible(true)
        )
    }

    override fun addMarker( position: LatLng, marker:Bitmap, cacheInfo:CacheSummaryModel):Marker? {
        val marker = mapObject?.addMarker(
            MarkerOptions()
                .position(position)
                .title(cacheInfo.name)
                .icon(BitmapDescriptorFactory.fromBitmap(marker))
                .draggable(false)
                .visible(true)
        )
        marker?.tag = cacheInfo.code
        return marker
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker == null || marker == currentLocationMarker) {
            // Return false to indicate that we have not consumed the event and that we wish
            // for the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            return false
        }
        selectedMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(MapMarker.DEFAULT.bitmap))
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(MapMarker.SELECTED_CACHE.bitmap))
        val code: String = marker.tag.toString()
        val name: String = marker.title
        MapInteractionDataStore.setSelectedCache(CacheSummaryModel(code, marker.position, name))
        selectedMarker = marker
        return true
    }

    override fun onMapClick(p0: LatLng?) {
        selectedMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(MapMarker.DEFAULT.bitmap))
        MapInteractionDataStore.setSelectedCache(null)
    }

    override fun onMyLocationButtonClick(): Boolean {
        centerOnCurrentLocation()
        return true
    }

}