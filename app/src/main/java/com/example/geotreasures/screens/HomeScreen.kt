package com.example.geotreasures.screens

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.example.geotreasures.components.CurrentLocationBtn
import com.example.geotreasures.components.GoogleMap
import com.example.geotreasures.components.CacheInfo
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.map.GoogleMapViewController
import com.example.geotreasures.map.MapInteractionDataStore


@ExperimentalAnimationApi
@Composable
fun HomeScreen() {
    val activeCacheDetails = MapInteractionDataStore.activeCache.observeAsState()
    val halfScreenHeightMap = remember { mutableStateOf(false) }
    val mapController = remember { mutableStateOf(GoogleMapViewController()) }
    halfScreenHeightMap.value = activeCacheDetails.value != null
    Column {
        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val tileHeight =
                        constraints.maxHeight / (if (halfScreenHeightMap.value) 2 else 1)
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = constraints.maxWidth,
                            maxWidth = constraints.maxWidth,
                            minHeight = tileHeight,
                            maxHeight = tileHeight,
                        )
                    )

                    layout(width = placeable.width, height = placeable.height) {
                        placeable.place(x = 0, y = 0, zIndex = 0f)
                    }
                }
            //.fillMaxSize()
        ) {
            // code, name to struct to prevent map reloading

            GoogleMap(mapController.value)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                    }
                ) {
                    CacheInfo()
                }

            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (activeCacheDetails.value != null) {
                CacheDetails(model = activeCacheDetails.value!!)
            }
        }
    }
}