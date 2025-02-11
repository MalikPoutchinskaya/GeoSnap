package com.kayakstudio.geosnap.ui.features.login.checkemail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.tools.android.OpenExternalApp
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showSuccessSnackbar
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.AuthScreenModel
import com.kayakstudio.geosnap.ui.features.login.login.LoginScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

data class CheckEmailScreen(private val userEmail: String) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<AuthScreenModel>() { parametersOf(userEmail) }
        val navigator = LocalNavigator.currentOrThrow
        val state by screenModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(userEmail) {
            screenModel.setEvent(AuthContract.Event.OnEmailChanged(userEmail))
        }

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is AuthContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )

                    AuthContract.Effect.ShowSuccessResendMessage ->
                        scope.showSuccessSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_success),
                            description = context.getString(R.string.checkEmailScreen_snackbar_resendConfirmation)
                        )

                    AuthContract.Effect.OpenMailApp -> {
                        val openExternalApp by inject<OpenExternalApp>()
                        openExternalApp.openEmailApp()
                    }

                    AuthContract.Effect.NavigateToLoginScreen ->
                        navigator.popUntil { it is LoginScreen }

                    else -> {}
                }
            }
        }

        CheckEmailView(state) { screenModel.setEvent(it) }
    }
}