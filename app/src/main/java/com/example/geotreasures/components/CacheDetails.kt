package com.example.geotreasures.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.geotreasures.components.HtmlText
import com.example.geotreasures.components.InfoCard
import com.example.geotreasures.data.CacheDetailsModel
import com.example.geotreasures.network.DataFetcher

@Composable
fun CacheDetails(model: CacheDetailsModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val scrollState = rememberScrollState()
    val scrollEnabled = remember{ mutableStateOf(false)}
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        //InfoCard(code = model.code, title = model.name)
        Box(Modifier
            .padding(10.dp)
        ) {
            HtmlText(
                model.description
            )
        }

    }
}

