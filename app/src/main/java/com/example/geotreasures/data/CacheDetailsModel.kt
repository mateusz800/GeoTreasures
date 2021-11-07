package com.example.geotreasures.data

import com.google.android.libraries.maps.model.LatLng
import com.google.gson.JsonObject

data class CacheDetailsModel(
    val code: String,
    val location: String,
    val name: String,
    val description: String
) {
}