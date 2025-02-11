package com.kayakstudio.geosnap.ui.features.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.features.camera.validation.UserGeoGuessValidationScreen
import kotlinx.coroutines.flow.collectLatest


object CameraScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: CameraScreenModel = navigator.getNavigatorScreenModel<CameraScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is CameraContract.Effect.ShowPhotoSentMessage,
                    is CameraContract.Effect.NavigateToCameraScreen -> {}

                    CameraContract.Effect.NavigateBack ->
                        navigator.pop()

                    is CameraContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )

                    CameraContract.Effect.OpenGallery -> {}

                    CameraContract.Effect.NavigateToGeoGuessValidationScreen ->
                        navigator.push(UserGeoGuessValidationScreen)
                }
            }
        }

        CameraScaffold(uiState = uiState) { screenModel.setEvent(it) }
    }
}
