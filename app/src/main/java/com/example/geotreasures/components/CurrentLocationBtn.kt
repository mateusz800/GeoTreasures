package com.example.geotreasures.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geotreasures.Singletons


@Composable
fun CurrentLocationBtn() {
    val context = LocalContext.current
    IconButton(
        onClick = {
            Singletons.mapController.centerOnCurrentLocation()
        },
        modifier = Modifier
            .then(Modifier.size(40.dp))
            .border(1.dp, Black, shape = CircleShape),
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Icon(
            Icons.Filled.Search,
            "Navigate to current location",
            tint = Black,
        )
    }
}

@Preview
@Composable
fun CurrentLocationBtnPreview() {
    CurrentLocationBtn()
}