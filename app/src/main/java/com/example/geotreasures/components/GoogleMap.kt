package com.example.geotreasures.components


import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.requestPermissions
import com.example.geotreasures.map.GoogleMapViewController
import com.example.geotreasures.map.MapInteractionDataStore
import com.google.android.libraries.maps.*


@Composable
fun GoogleMap() {
    val context = LocalContext.current
    val controller = GoogleMapViewController()

    val map = controller.createMapView()
    requestPermissions(
        context as Activity,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        1
    )
    Surface(color = MaterialTheme.colors.background) {
        AndroidView({ map }, modifier = Modifier.fillMaxSize()) { mapView ->
            mapView.getMapAsync {
                it.moveCamera(CameraUpdateFactory.zoomBy(10f))
                controller.setMapObject(it)
                controller.centerOnCurrentLocation()
            }
        }
    }
}


/*
@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    GoogleMap(
        onSelectedMarkerChange = (String, String) ->
    )
}
 */