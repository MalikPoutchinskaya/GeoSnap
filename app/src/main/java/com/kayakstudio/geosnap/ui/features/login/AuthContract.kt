package com.kayakstudio.geosnap.ui.features.login

import com.kayakstudio.geosnap.ui.design.components.ccp.AccountPhoneNumber
import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class AuthContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object ShowSuccessResendMessage : Effect()
        data object OpenMailApp : Effect()
        data object NavigateToLoginScreen : Effect()
        data object NavigateToMainScreen : Effect()
        data object NavigateToForgotPasswordScreen : Effect()
        data object NavigateToSignupScreen : Effect()
        data class NavigateToCheckEmailScreen(val userEmail:String) : Effect()
    }

    sealed class Event : UiEvent {
        data class OnEmailChanged(val userEmail: String) : Event()
        data class OnFirstNameChanged(val userFirstName: String) : Event()
        data class OnLastNameChanged(val userLastName: String) : Event()
        data class OnAccountPhoneNumberChanged(val accountPhoneNumber: AccountPhoneNumber) : Event()
        data class OnPasswordChanged(val password: String) : Event()
        data object OnUserLogin : Event()
        data object OnUserLoginWithGoogle : Event()
        data object OnUserSignup : Event()
        data object OnUserSignupWithGoogle : Event()
        data object OnUserResetPassword : Event()
        data object OnUserResendForgotPassword : Event()
        data object OnUserOpenEmail : Event()
        data object OnUserNavigateToLogin : Event()
        data object OnUserNavigateToSignup : Event()
        data object OnUserNavigateToForgotPassword : Event()
    }

    data class State(
        val userFirstName: String,
        val userLastName: String,
        val userEmail: String,
        val password: String,
        val accountPhoneNumber: AccountPhoneNumber,
        val isLoading: Boolean,
        val isLoggedIn: Boolean,
        val isSignedUp: Boolean,
    ) : UiState
}
