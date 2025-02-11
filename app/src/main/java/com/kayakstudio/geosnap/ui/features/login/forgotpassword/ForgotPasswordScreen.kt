package com.kayakstudio.geosnap.ui.features.login.forgotpassword

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
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.AuthScreenModel
import com.kayakstudio.geosnap.ui.features.login.checkemail.CheckEmailScreen
import com.kayakstudio.geosnap.ui.features.login.login.LoginScreen
import kotlinx.coroutines.flow.collectLatest

object ForgotPasswordScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<AuthScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        val state by screenModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is AuthContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )

                    AuthContract.Effect.NavigateToLoginScreen ->
                        navigator.popUntil { it is LoginScreen }

                    is AuthContract.Effect.NavigateToCheckEmailScreen ->
                        navigator.push(CheckEmailScreen(effect.userEmail))

                    else -> {}
                }
            }
        }

        ForgotPasswordView(state) { screenModel.setEvent(it) }
    }
}