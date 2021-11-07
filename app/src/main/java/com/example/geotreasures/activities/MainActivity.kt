package com.example.geotreasures.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.geotreasures.map.MapInteractionDataStore
import com.example.geotreasures.screens.HomeScreen
import com.example.geotreasures.ui.theme.GeoTreasuresTheme

class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoTreasuresTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }

    override fun onBackPressed() {
        if(MapInteractionDataStore.activeCache.value != null){
            MapInteractionDataStore.activeCache.postValue(null)
        } else {
            super.onBackPressed()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GeoTreasuresTheme {

    }
}



