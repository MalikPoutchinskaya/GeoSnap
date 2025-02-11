package com.kayakstudio.geosnap.ui.features.camera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import com.kayakstudio.geosnap.ui.features.camera.components.InstagramCameraButton
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.ui.camera.CameraMode
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState


@Composable
fun CameraScaffold(
    uiState: CameraContract.State,
    onEvent: (CameraContract.Event) -> Unit,
) {
    val resizeOptions = ResizeOptions(
        resizeThresholdBytes = 1048576L, // 1MB
        compressionQuality = 0.5 // Adjust compression quality (0.0 to 1.0)
    )

    val cameraState = rememberPeekabooCameraState(
        initialCameraMode = CameraMode.Front,
        onCapture = { img ->
            img?.let {
                onEvent(CameraContract.Event.OnPhotoCaptured(img))
            }
        })

    Box(modifier = Modifier.fillMaxSize()) {
        PeekabooCamera(
            state = cameraState,
            modifier = Modifier.fillMaxSize(),
            permissionDeniedContent = {
                PermissionDenied(
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
        CameraOverlay(
            isCapturing = uiState.isCapturing,
            onBackClicked = { onEvent(CameraContract.Event.OnUserClickOnPhotoClose) },
            onCaptureClicked = {
                onEvent(CameraContract.Event.OnUserClickOnPhotoCapture)
                cameraState.capture()
            }

        )
    }
}


@Composable
fun PermissionDenied(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
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
            text = "Please grant the camera permission!",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CameraOverlay(
    isCapturing: Boolean,
    onBackClicked: () -> Unit,
    onCaptureClicked: () -> Unit,
) {
    val bgColor = Color(0x77000000)

    Scaffolds.SafePadding(
        useDefaultHorizontalScreenPadding = false,
        containerColor = Color.Transparent,
        topBar = {
            AppBars.Top(
                title = "",
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = bgColor)
            ) { onBackClicked() }
        },
        bottomBar = {
            NavigationBar(
                containerColor = bgColor,
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    if (isCapturing) {
                        Column(
                            Modifier.align(Alignment.BottomCenter)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(bottom = 16.dp),
                                color = Color.White.copy(alpha = 0.7f),
                                strokeWidth = 8.dp,
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    } else {
                        InstagramCameraButton(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            onClick = onCaptureClicked,
                        )
                    }
                }
            }
        },
        content = {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                TransparentClipLayout(
                    modifier = Modifier.fillMaxSize(),
                    width = maxWidth - Dimens.horizontalScreenPadding,
                    height = maxHeight - Dimens.verticalScreenPadding,
                    offsetY = 0.dp,
                    bgColor = bgColor
                )
            }
        }
    )
}

@Composable
fun TransparentClipLayout(
    modifier: Modifier,
    width: Dp,
    height: Dp,
    offsetY: Dp,
    bgColor: Color
) {

    val offsetInPx: Float
    val widthInPx: Float
    val heightInPx: Float

    with(LocalDensity.current) {
        offsetInPx = offsetY.toPx()
        widthInPx = width.toPx()
        heightInPx = height.toPx()
    }

    Canvas(modifier = modifier) {

        val canvasWidth = size.width

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawRect(bgColor)

            // Source
            drawRoundRect(
                topLeft = Offset(
                    x = (canvasWidth - widthInPx) / 2,
                    y = offsetInPx
                ),
                size = Size(widthInPx, heightInPx),
                cornerRadius = CornerRadius(30f, 30f),
                color = Color.Transparent,
                blendMode = BlendMode.Clear
            )
            restoreToCount(checkPoint)
        }

    }
}