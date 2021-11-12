package com.example.geotreasures.screens

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.widget.ConstraintSet
import com.example.geotreasures.MainApplication
import com.example.geotreasures.components.CurrentLocationBtn
import com.example.geotreasures.components.GoogleMap
import com.example.geotreasures.components.CacheInfo
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.map.GoogleMapViewController
import com.example.geotreasures.map.MapInteractionDataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@Composable
fun HomeScreen() {
    val activeCacheDetails = MapInteractionDataStore.activeCache.observeAsState()
    val activeCacheDetailsBackup = MapInteractionDataStore.activeCacheBackup
    val scrollState = rememberScrollState()
    val previousScrollState = remember{ mutableStateOf(scrollState.value)}
    val areDetailsOnTop = remember { mutableStateOf(false)}
    val swipeUp = remember{ mutableStateOf(false)}
    val cardPadding = animateIntAsState(
        targetValue = if(activeCacheDetails.value != null) 0 else 10,
        animationSpec = tween( delayMillis = if(activeCacheDetails.value!=null) 0 else 1000)
    )
    val cardOffset = animateIntAsState(
        targetValue = if (activeCacheDetails.value != null) 5 else 0,
        animationSpec = tween(delayMillis = if (activeCacheDetails.value != null) 0 else 850)
    )
    val mapHeightCoefficient = animateFloatAsState(
        targetValue = if (activeCacheDetails.value != null) 2f else 1f,
        animationSpec = tween(1000)
    )
    val mapHeightSubtraction = animateFloatAsState(
        targetValue = if(swipeUp.value) 2*LocalConfiguration.current.screenHeightDp.toFloat() else 0f,
        animationSpec = tween(1000)
    )
    var mapTileHeight: Int
    val halfScreenHeightMap = remember { mutableStateOf(false) }

    halfScreenHeightMap.value = activeCacheDetails.value != null
    if(activeCacheDetails.value == null){
        swipeUp.value = false
    }

    Column {
        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    mapTileHeight =
                        ((constraints.maxHeight / mapHeightCoefficient.value).roundToInt() - mapHeightSubtraction.value).roundToInt()
                    if (mapTileHeight < 220) {
                        mapTileHeight = 220
                        if (!areDetailsOnTop.value) {
                            previousScrollState.value = scrollState.value
                        }
                        areDetailsOnTop.value = true
                    } else {
                        areDetailsOnTop.value = false
                    }
                    val placeable = measurable.measure(
                        constraints.copy(
                            minWidth = constraints.maxWidth,
                            maxWidth = constraints.maxWidth,
                            minHeight = mapTileHeight,
                            maxHeight = mapTileHeight,
                        )
                    )
                    layout(width = placeable.width, height = placeable.height) {
                        placeable.place(
                            x = 0,
                            y = if (mapTileHeight <= 220) -25 else 0,
                            zIndex = 2f
                        )
                    }
                }
        ) {
            Box(modifier = Modifier.alpha(min(1f, 1 - (mapHeightSubtraction.value / 700f)))) {
                GoogleMap()
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Box(modifier = Modifier
                    .padding(cardPadding.value.dp)
                    .offset(y = cardOffset.value.dp)
                    .clickable {
                    }
                ) {
                    CacheInfo()
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .zIndex(-1f)
            .verticalScroll(state = scrollState)
            .pointerInput(Unit) {
                if (!swipeUp.value) {
                    detectVerticalDragGestures { change, dragAmount ->
                        if (dragAmount < 0) {
                            swipeUp.value = true
                        } else if (scrollState.value == 0 && dragAmount > 0) {
                            if (!swipeUp.value && mapHeightSubtraction.value == 0f) {
                                MapInteractionDataStore.setActiveCache(null)
                            }
                            swipeUp.value = false
                        }
                    }
                }
            }
        ) {
            if (activeCacheDetailsBackup != null) {
                CacheDetails(model = activeCacheDetailsBackup)
            }
        }
    }
}