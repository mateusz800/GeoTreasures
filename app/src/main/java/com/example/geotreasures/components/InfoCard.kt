package com.example.geotreasures.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geotreasures.ui.theme.Shapes

@Composable
fun InfoCard(code:String, title:String){
    Column(
        modifier = Modifier
            .background(Color.White, Shapes.small)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()

    ) {
        Text(code, fontSize = 12.sp)
        // TODO: name in one line with animation (if text not entire visible)
        Text(title, fontSize = 18.sp)

    }
}

@Preview
@Composable
fun InfoCardPreview(){
    InfoCard(code="OPXY78", title = "Rozbójnik 19")
}