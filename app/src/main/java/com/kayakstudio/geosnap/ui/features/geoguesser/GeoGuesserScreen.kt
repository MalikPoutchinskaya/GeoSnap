package com.kayakstudio.geosnap.ui.features.geoguesser

import android.location.Location
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.data.playerandpicture.PlayerAndPictureModel
import com.kayakstudio.geosnap.indus.analytics.Analytics
import com.kayakstudio.geosnap.indus.analytics.AnalyticsEvent
import com.kayakstudio.geosnap.indus.analytics.TrackScreen
import com.kayakstudio.geosnap.ui.design.components.*
import com.kayakstudio.geosnap.ui.design.templates.Templates
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collectLatest
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

object GeoGuesserScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val analytics by inject<Analytics>()
        TrackScreen(analytics, AnalyticsEvent.ScreenName.GEO_GUESSER)

        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: GeoGuesserScreenModel = getScreenModel()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is GeoGuesserContract.Effect.NavigateBack -> navigator.pop()
                    is GeoGuesserContract.Effect.ShowErrorMessage -> scope.showErrorSnackbar(
                        title = context.getString(R.string.defaultSnackbar_title_error),
                        description = effect.message
                    )
                }
            }
        }

        GeoGuesserScaffold(state) { screenModel.setEvent(it) }
    }
}

@Composable
fun GeoGuesserScaffold(
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
    val gradient = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    )
    val buttonEvent = if (state.isRevealed) GeoGuesserContract.Event.OnNext else {
        GeoGuesserContract.Event.OnReveal(
            playerId = playerAndPicture?.player?.id.orEmpty(),
            pictureId = playerAndPicture?.picture?.id.orEmpty(),
            selectedLocation = cameraPositionState.position.target
        )
    }
    val showResult = state.isRevealed && playerAndPicture != null && playerPictureResult != null

    Box(Modifier.background(brush = gradient)) {
        when {
            state.isLoading -> Templates.Loading()
            state.isFinished || playerAndPicture == null -> Templates.Empty(
                title = stringResource(R.string.geoGuesserScreen_done_title),
                description = stringResource(R.string.geoGuesserScreen_done_description)
            )
            else -> Column(modifier = Modifier.fillMaxSize()) {
                Box(Modifier.weight(1f)) {
                    Pictures.GeoGuesserImage(
                        asyncPainterResource(
                            data = playerAndPicture.picture.url,
                            key = state.index
                        )
                    )
                }
            }
        }
    }
}
