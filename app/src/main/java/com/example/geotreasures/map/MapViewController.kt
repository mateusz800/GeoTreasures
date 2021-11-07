package com.example.geotreasures.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import com.example.geotreasures.MainApplication
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.map.MapInteractionDataStore
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker


abstract class MapViewController {

    private val context:Context = MainApplication.applicationContext()

    abstract fun centerOnCurrentLocation()
    abstract fun addMarker(position: LatLng, marker:Bitmap): Marker?
    abstract fun addMarker(position: LatLng, marker: Bitmap, cacheInfo:CacheSummaryModel):Marker?

    fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}