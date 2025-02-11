package com.kayakstudio.geosnap.ui.features.profile.settings

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class SettingsContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
        data object NavigateToUpdatePasswordScreen : Effect()
    }

    sealed class Event : UiEvent {
        data object OnBackClicked : Event()
        data object OnUpdatePasswordClicked : Event()
        data object OnLogoutRequestClicked : Event()
        data object OnLogoutConfirmationClicked : Event()
        data object OnDismissLogoutDialogClicked : Event()
        data object OnNotificationsChanged : Event()
        data object OnDarkModeChanged : Event()
        data object OnSave : Event()
    }

    data class State(
        val isNotificationsEnabled: Boolean,
        val isDarkModeEnabled: Boolean,
        val showLogoutConfirmationDialog: Boolean
    ) : UiState
}
