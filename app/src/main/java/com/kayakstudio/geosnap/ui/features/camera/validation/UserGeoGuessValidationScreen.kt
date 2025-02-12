package com.kayakstudio.geosnap.ui.features.camera.validation

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
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showSuccessSnackbar
import com.kayakstudio.geosnap.ui.features.camera.frame.CameraContract
import com.kayakstudio.geosnap.ui.features.camera.frame.CameraScreenModel
import kotlinx.coroutines.flow.collectLatest

object UserGeoGuessValidationScreen : Screen {

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
                    CameraContract.Effect.NavigateToGeoGuessValidationScreen -> {
                        // nothing here, using shared VM to simplify logic
                    }

                    CameraContract.Effect.NavigateBack ->
                        navigator.popUntilRoot()

                    is CameraContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )

                    CameraContract.Effect.ShowPhotoSentMessage ->
                        scope.showSuccessSnackbar(
                            title = "Geoguess picture sent!",
                            description = "Your friend will shortly try to find you :)"
                        )
                }
            }
        }

        UserGeoGuessValidationScaffold(uiState) { screenModel.setEvent(it) }
    }
}