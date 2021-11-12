package com.example.geotreasures.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geotreasures.R


@Composable
fun CloseButton(onClick:()->Unit){
    val launched = remember{ mutableStateOf(false)}
    val alpha = animateFloatAsState(targetValue = if(launched.value) 1f else 0f)
    LaunchedEffect(key1 = Unit, block = {
        launched.value = true
    } )
    Image(Icons.Rounded.Close,
        contentDescription = "Close icon",
        colorFilter = ColorFilter.tint(Color.White),
        modifier = Modifier
            .clickable { onClick() }
            .padding(5.dp)
            .alpha(alpha = alpha.value)
            .height(30.dp)
            .width(30.dp)
    )
}

@Preview
@Composable
fun CloseButtonPreview(){
    CloseButton(onClick = {/*do nothing*/})
}