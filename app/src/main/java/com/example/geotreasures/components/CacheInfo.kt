package com.example.geotreasures.components

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.geotreasures.data.CacheDetailsModel
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.map.MapInteractionDataStore
import com.example.geotreasures.network.DataFetcher
import kotlinx.coroutines.*

internal const val ANIMATION_DURATION: Long = 250


// TODO: Expand card size to full size on click with animation

private class SelectedCacheObserver(onDeselectCache: () -> (Unit), onSelectCache: () -> (Unit)) {
    var previousModel: CacheSummaryModel? = null
    var displayedModel: CacheSummaryModel? = null

    companion object {
        var instance: SelectedCacheObserver? = null
    }

    init {
        instance = this
        MapInteractionDataStore.selectedCache.observeForever { model ->
            if (model == null && previousModel != null) {
                onDeselectCache()
            } else if (previousModel != null && model != null && previousModel != model) {
                // Selected another cache
                onDeselectCache()
                MainScope().launch {
                    delay(ANIMATION_DURATION)
                    displayedModel = model
                    onSelectCache()
                }

            } else if (model != null && model != previousModel) {
                displayedModel = model
                onSelectCache()
            }
            previousModel = model
        }
    }
}

private fun showDetails(cacheCode: String?) {
    if (cacheCode == null) {
        return
    }
    MainScope().launch {
        MapInteractionDataStore.setActiveCache(DataFetcher.fetchCacheDetails(cacheCode))
    }
}

@ExperimentalAnimationApi
@Composable
fun CacheInfo() {
    var launched = remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val observer: SelectedCacheObserver
    if (SelectedCacheObserver.instance == null) {
        observer = SelectedCacheObserver(
            onDeselectCache = {
                launched.value = false
            },
            onSelectCache = {
                launched.value = true
            }
        )
    } else {
        observer = SelectedCacheObserver.instance!!
    }
    val cache: CacheSummaryModel? = observer.displayedModel
    val context: Context = LocalContext.current

    AnimatedVisibility(
        visible = launched.value,
        enter = slideInVertically(
            initialOffsetY = { with(density) { 120.dp.roundToPx() } },
            animationSpec = tween(ANIMATION_DURATION.toInt())
        ),
        exit = slideOut(
            targetOffset = {
                IntOffset(0, with(density) { 130.dp.roundToPx() })
            },
            animationSpec = tween(ANIMATION_DURATION.toInt())
        ),
        modifier = Modifier
            .clickable { showDetails(cache?.code) }
    ) {
        InfoCard(
            code = cache?.code ?: "", title = cache?.name ?: ""
        )

    }
}

