package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

object Pictures {
    @Composable
    fun Avatar(imageUrl: String, height: Dp=80.dp) {
        Avatar(asyncPainterResource(data = imageUrl), height)
    }

    @Composable
    fun Avatar(imageBitmap: ImageBitmap?) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "imageBitmap",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(1f)
                    .clip(shape = CircleShape),
            )
        } else {
            Avatar(asyncPainterResource(data = ""))
        }
    }

    @Composable
    fun Avatar(res: Resource<Painter>, height: Dp=80.dp) {
        KamelImage(
            resource = { res },
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(height)
                .aspectRatio(1f)
                .clip(shape = CircleShape),
            onLoading = {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            },
            onFailure = {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainer,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(56.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person",
                        tint = Color.LightGray
                    )
                }
            },
        )
    }

    @Composable
    fun GeoGuesserImage(res: Resource<Painter>) {
        KamelImage(
            resource = { res },
            contentDescription = "GeoGuesserImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().aspectRatio(1f),
            onLoading = {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            },
            onFailure = {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainer,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(56.dp),
                        imageVector = Icons.Default.Landscape,
                        contentDescription = "Landscape",
                        tint = Color.LightGray
                    )
                }
            },
        )
    }
}