package com.example.geotreasures.data

import com.google.android.libraries.maps.model.LatLng

data class CacheSummaryModel(
    val code: String,
    val location: LatLng,
    val name: String
) {

}