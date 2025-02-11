package com.kayakstudio.geosnap.ui.features.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.ui.gallery.ExperimentalPeekabooGalleryApi
import com.preat.peekaboo.ui.gallery.PeekabooGallery

@OptIn(ExperimentalPeekabooGalleryApi::class)
@Composable
internal fun PeekabooGalleryView(
    modifier: Modifier = Modifier,
    onImageSelected: (ByteArray?) -> Unit,
    onBack: () -> Unit,
) {
    Box(modifier = modifier) {
        PeekabooGallery(
            modifier = Modifier.fillMaxSize()   ,
            header = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Back Button",
                            tint = Color.White,
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "PICK A PHOTO",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    )
                }
            },
            progressIndicator = {
                CircularProgressIndicator(
                    modifier =
                    Modifier
                        .size(60.dp)
                        .align(Alignment.Center),
                    color = Color.White,
                    strokeWidth = 6.dp,
                )
            },
            permissionDeniedContent = {
                Column(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning Icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Please grant the storage permission!",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                }
            },
            onImageSelected = onImageSelected,
        )
    }
}