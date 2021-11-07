package com.example.geotreasures.utility

import android.content.Context
import com.google.android.libraries.maps.model.LatLng
import java.lang.Double.parseDouble
import android.graphics.Bitmap
import android.graphics.Canvas

import androidx.core.graphics.drawable.DrawableCompat

import android.os.Build

import androidx.core.content.ContextCompat

import android.graphics.drawable.Drawable
import com.example.geotreasures.MainApplication


object ConvertUtility {
    fun getLatLngFromString(string: String) : LatLng{
        val value = string.replace("\"", "")
        val array = value.split("|")
        val latitude = parseDouble(array[0])
        val longitude = parseDouble(array[1])
        return LatLng(latitude, longitude)
    }
    fun getBitmapFromVectorDrawable( drawableId: Int): Bitmap? {
        val context = MainApplication.applicationContext()
        var drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }
}