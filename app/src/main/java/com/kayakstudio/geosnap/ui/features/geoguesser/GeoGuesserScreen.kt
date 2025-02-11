package com.kayakstudio.geosnap.ui.features.geoguesser

import android.location.Location
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.data.playerandpicture.PlayerAndPictureModel
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Pictures
import com.kayakstudio.geosnap.ui.design.components.ProfileMapMarker
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.design.templates.Templates
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collectLatest
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit


object GeoGuesserScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: GeoGuesserScreenModel = getScreenModel<GeoGuesserScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is GeoGuesserContract.Effect.NavigateBack ->
                        navigator.pop()

                    is GeoGuesserContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        GeoGuesserContent(state) { screenModel.setEvent(it) }
    }
}

@Composable
fun GeoGuesserContent(
    state: GeoGuesserContract.State,
    onEvent: (GeoGuesserContract.Event) -> Unit
) {
    val playerAndPicture = state.playersAndPictures.getOrNull(state.index)
    val playerPictureResult = state.playerPictureResults.getOrNull(state.index)
    val bredCrumbSize = state.playersAndPictures.size
    val bredCrumbIndex = state.index
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLatLng, 10f)
    }
    val bgBrushedColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
    )
    val gradient = Brush.horizontalGradient(bgBrushedColors)
    val buttonEvent = when {
        state.isRevealed -> GeoGuesserContract.Event.OnNext
        else -> {
            val selectedLocation = cameraPositionState.position.target

            GeoGuesserContract.Event.OnReveal(
                playerId = playerAndPicture?.player?.id ?: "", //fixme
                pictureId = playerAndPicture?.picture?.id ?: "",
                selectedLocation = selectedLocation
            )
        }
    }
    val showResult = state.isRevealed && playerAndPicture != null && playerPictureResult != null

    Box(Modifier.background(brush = gradient)) {
        when {
            state.isLoading -> Templates.Loading()
            state.isFinished || playerAndPicture == null -> Templates.Empty(
                title = "You're all done!",
                description = "Find new friends to view more content and gain more points."
            )

            else ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(Modifier.weight(1f)) {
                        Pictures.GeoGuesserImage(
                            asyncPainterResource(
                                data = playerAndPicture.picture.url,
                                key = state.index
                            )
                        )

                        this@Column.AnimatedVisibility(
                            visible = showResult,
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center)
                        ) {
                            if (showResult) {
                                val (distance, isSuccess) = getDistanceAndIsSuccess(
                                    playerPictureResult!!,
                                    playerAndPicture
                                )
                                val (containerColor, textColor) = if (isSuccess) listOf(
                                    MaterialTheme.colorScheme.tertiaryContainer,
                                    MaterialTheme.colorScheme.onTertiaryContainer
                                ) else
                                    listOf(
                                        MaterialTheme.colorScheme.errorContainer,
                                        MaterialTheme.colorScheme.onErrorContainer
                                    )
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Center)
                                        .background(containerColor.copy(alpha = 0.7f))
                                ) {
                                    Texts.Title(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = "$distance meters!",
                                        color = textColor
                                    )
                                }
                            }
                        }


                        AssistChip(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(horizontal = 24.dp),
                            colors = AssistChipDefaults.assistChipColors().copy(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                                    alpha = 0.7f
                                ),
                                labelColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.7f
                                ),
                            ),
                            label = { Texts.SubTitle(playerAndPicture.player.displayName) },
                            onClick = {}
                        )
                    }
                    Box(Modifier.weight(1f)) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ) {
                            if (state.isRevealed && playerPictureResult != null) {
                                val selectedLocation = playerPictureResult.selectedLocation
                                val realLocation = LatLng(
                                    playerAndPicture.picture.location.lat,
                                    playerAndPicture.picture.location.lng
                                )
                                val selectedMarkerState = remember {
                                    MarkerState(position = selectedLocation)
                                }
                                MarkerComposable(
                                    state = selectedMarkerState,
                                    anchor = Offset(0.5f, 1f),
                                ) {
                                    ProfileMapMarker(
                                        modifier = Modifier.align(Alignment.Center),
                                        imageUrl = playerAndPicture.player.imageUrl ?: "",
                                        fullName = playerAndPicture.player.displayName,
                                    )
                                }

                                val markerState = remember {
                                    MarkerState(position = realLocation)
                                }

                                MarkerComposable(
                                    state = markerState,
                                    anchor = Offset(0.5f, 1f),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "RealLocation",
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )

                                }

                                Polyline(
                                    points = listOf(selectedLocation, realLocation),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    width = 4f
                                )
                            }
                        }

                        if (state.isRevealed.not()) {
                            ProfileMapMarker(
                                modifier = Modifier.align(Alignment.Center),
                                imageUrl = playerAndPicture.player.imageUrl ?: "",
                                fullName = playerAndPicture.player.displayName,
                            )
                        }
                    }
                }
        }

        if (state.isFinished.not()) {
            Breadcrumb(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .padding(
                        horizontal = Dimens.horizontalScreenPadding,
                        vertical = Dimens.verticalScreenPadding
                    ),
                bredCrumbSize = bredCrumbSize,
                bredCrumbIndex = bredCrumbIndex
            )
        }


        if (showResult) {
            val (_, isSuccess) = getDistanceAndIsSuccess(
                playerPictureResult!!,
                playerAndPicture!!
            )
            if (isSuccess) {
                KonfettiView(
                    modifier = Modifier.fillMaxSize(),
                    parties = parade(),
                )
            }
        }

        if (playerAndPicture != null) {
            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                isFinished = state.isFinished,
                isRevealed = state.isRevealed,
            ) {
                onEvent(buttonEvent)
            }
        }

    }
}

@Composable
private fun getDistanceAndIsSuccess(
    playerPictureResult: PlayerPictureResult,
    playerAndPicture: PlayerAndPictureModel
): Pair<Float, Boolean> {
    val selectedLocation = playerPictureResult.selectedLocation
    val realLocation = LatLng(
        playerAndPicture.picture.location.lat,
        playerAndPicture.picture.location.lng
    )
    val distance = calculateDistanceInMeters(selectedLocation, realLocation)
    val isSuccess = distance < DISTANCE_SUCCESS_IN_METERS
    return Pair(distance, isSuccess)
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    isFinished: Boolean,
    isRevealed: Boolean,
    onClick: () -> Unit
) {
    if (isFinished.not()) {
        val text = when {
            isRevealed -> "NEXT"
            else -> "REVEAL"
        }

        AppBars.Bottom(
            modifier = modifier,
            firstButtonText = text,
            onFirstButtonClicked = { onClick() },
        )
    }
}

@Composable
private fun Breadcrumb(modifier: Modifier, bredCrumbSize: Int, bredCrumbIndex: Int) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0..<bredCrumbSize) {
            val isSelected = i == bredCrumbIndex
            val color =
                if (isSelected) MaterialTheme.colorScheme.tertiaryContainer else Color.White

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(16.dp),
                    )
            )
        }
    }
}


private fun calculateDistanceInMeters(start: LatLng, end: LatLng): Float {
    val results = FloatArray(1)
    Location.distanceBetween(
        start.latitude, start.longitude,
        end.latitude, end.longitude,
        results
    )
    return results[0] // Distance en mètres
}

private val defaultLatLng = LatLng(48.8589383, 2.2644632)
private const val DISTANCE_SUCCESS_IN_METERS = 10000
private fun parade(): List<Party> {
    val party = Party(
        angle = Angle.RIGHT - 45,
        spread = Spread.SMALL,
        emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(30),
        position = Position.Relative(0.0, 0.5)
    )

    return listOf(
        party,
        party.copy(
            angle = party.angle - 90, // flip angle from right to left
            position = Position.Relative(1.0, 0.5)
        ),
    )
}