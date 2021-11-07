package com.example.geotreasures.map

import android.graphics.Bitmap
import com.example.geotreasures.R
import com.example.geotreasures.utility.ConvertUtility

enum class MapMarker(val bitmap: Bitmap) {
    CURRENT_LOCATION(ConvertUtility.getBitmapFromVectorDrawable( R.drawable.marker_user_location)!!),
    SELECTED_CACHE(ConvertUtility.getBitmapFromVectorDrawable( R.drawable.marker_active)!!),
    DEFAULT(ConvertUtility.getBitmapFromVectorDrawable( R.drawable.marker)!!),
}