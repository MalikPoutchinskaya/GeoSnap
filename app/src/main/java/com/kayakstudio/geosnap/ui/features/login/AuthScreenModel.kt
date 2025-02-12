package com.kayakstudio.geosnap.ui.features.login

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelper
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.tools.extensions.safeMessage
import com.kayakstudio.geosnap.ui.design.components.ccp.AccountPhoneNumber
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class AuthScreenModel(
    private val authHelper: FirebaseAuthHelper,
    private val userRepository: UserRepository,
) : MviScreenModel<AuthContract.Event, AuthContract.State, AuthContract.Effect>() {

    override fun createInitialState(): AuthContract.State {
        return AuthContract.State(
            userFirstName = "",
            userLastName = "",
            userEmail = "",
            password = "",
            newPassword = "",
            accountPhoneNumber = AccountPhoneNumber.init(),
            isLoading = false,
            isLoggedIn = false,
            isSignedUp = false
        )
    }

    override fun handleEvent(event: AuthContract.Event) {
        when (event) {
            is AuthContract.Event.OnFirstNameChanged ->
                setState { copy(userFirstName = event.userFirstName) }

            is AuthContract.Event.OnLastNameChanged ->
                setState { copy(userLastName = event.userLastName) }

            is AuthContract.Event.OnEmailChanged ->
                setState { copy(userEmail = event.userEmail) }

            is AuthContract.Event.OnAccountPhoneNumberChanged ->
                setState { copy(accountPhoneNumber = event.accountPhoneNumber) }

            is AuthContract.Event.OnPasswordChanged ->
                setState { copy(password = event.password) }

            is AuthContract.Event.OnUserLogin ->
                login()

            is AuthContract.Event.OnUserSignup ->
                signup()

            is AuthContract.Event.OnUserResetPassword ->
                resetPassword(isResend = false)

            is AuthContract.Event.OnUserOpenEmail ->
                setEffect { AuthContract.Effect.OpenMailApp }

            is AuthContract.Event.OnUserLoginWithGoogle -> TODO()
            is AuthContract.Event.OnUserSignupWithGoogle -> TODO()

            is AuthContract.Event.OnUserNavigateToForgotPassword ->
                setEffect { AuthContract.Effect.NavigateToForgotPasswordScreen }

            is AuthContract.Event.OnUserNavigateToLogin ->
                setEffect { AuthContract.Effect.NavigateToLoginScreen }

            is AuthContract.Event.OnUserNavigateToSignup ->
                setEffect { AuthContract.Effect.NavigateToSignupScreen }

            is AuthContract.Event.OnUserResendForgotPassword ->
                resetPassword(isResend = true)

            is AuthContract.Event.OnNewPasswordChanged ->
                setState { copy(newPassword = event.password) }
        }
    }

    /**
     *
     */
    private fun login() {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
            userRepository.login(currentState.userEmail, currentState.password)
                .onSuccess { _ ->
                    setState { copy(isLoading = false, isLoggedIn = true) }
                }.onFailure {
                    setState { copy(isLoading = false) }
                    setEffect { AuthContract.Effect.ShowErrorMessage(it.safeMessage()) }
                }
        }
    }

    /**
     *
     */
    private fun signup() {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
            userRepository.register(
                email = currentState.userEmail,
                firstName = currentState.userFirstName,
                lastName = currentState.userLastName,
                phoneNumber = currentState.accountPhoneNumber.assembleFullPhoneNumber(),
                password = currentState.newPassword
            )
                .onSuccess {
                    setState { copy(isLoading = false) }
                    setEffect { AuthContract.Effect.NavigateToLoginScreen }
                }
                .onFailure {
                    setState { copy(isLoading = false) }
                    setEffect { AuthContract.Effect.ShowErrorMessage(it.safeMessage()) }
                }
        }
    }

    /**
     *
     */
    private fun resetPassword(isResend: Boolean) {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
            authHelper.sendPasswordResetEmail(currentState.userEmail)
                .onSuccess {
                    setState { copy(isLoading = false) }
                    if (isResend) {
                        setEffect { AuthContract.Effect.ShowSuccessResendMessage }
                    } else {
                        setEffect { AuthContract.Effect.NavigateToCheckEmailScreen(currentState.userEmail) }
                    }
                }
                .onFailure {
                    setState { copy(isLoading = false) }
                    setEffect { AuthContract.Effect.ShowErrorMessage(it.safeMessage()) }
                }
        }
    }
}
