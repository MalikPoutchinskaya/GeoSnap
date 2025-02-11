package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.asyncPainterResource

@Composable
fun ProfileMapMarker(
    modifier: Modifier,
    imageUrl: String,
    fullName: String,
) {
    val shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 0.dp)
    val painter = asyncPainterResource(data = imageUrl)

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            Pictures.Avatar(painter)
        } else {
            Text(
                text = fullName.take(1).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}