package com.kayakstudio.geosnap.ui.features.camera.validation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.features.camera.frame.CameraContract
import com.kayakstudio.geosnap.ui.tools.extensions.convertToImageBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserGeoGuessValidationScaffold(
    uiState: CameraContract.State,
    onEvent: (CameraContract.Event) -> Unit,
) {
    val bgBrushedColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
    )
    val gradient = Brush.horizontalGradient(bgBrushedColors)

    Box(Modifier.background(brush = gradient)) {
        Scaffolds.SafePadding(
            containerColor = Color.Transparent,
            topBar = {
                AppBars.Top(
                    title = stringResource(R.string.validationScreen_title),
                    colors = TopAppBarDefaults.topAppBarColors()
                        .copy(containerColor = Color.Transparent)
                ) { onEvent(CameraContract.Event.OnUserClickOnBack) }
            },
            bottomBar = {
                AppBars.Bottom(
                    isLoading = uiState.isSending,
                    firstButtonPainter = rememberVectorPainter(Icons.Default.Check),
                    firstButtonText = stringResource(R.string.validationScreen_validate_button),
                    onFirstButtonClicked = { onEvent(CameraContract.Event.OnUserValidatePhoto) },
                )
            },
            content = {
                UserGeoGuessImage(uiState.img)
            })
    }
}

@Composable
private fun UserGeoGuessImage(img: ByteArray?) {
    img?.convertToImageBitmap()?.let { bitmap ->
        Box(
            Modifier.fillMaxWidth()
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = stringResource(R.string.validationScreen_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    ),
            )
        }
    }
}

