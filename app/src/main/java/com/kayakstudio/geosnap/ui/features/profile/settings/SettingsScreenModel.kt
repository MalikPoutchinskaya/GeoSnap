package com.kayakstudio.geosnap.ui.features.profile.settings

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.domain.LogoutUseCase
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val appRepository: AppRepository,
    private val logoutUseCase: LogoutUseCase,
) : MviScreenModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>() {

    override fun createInitialState(): SettingsContract.State {
        return SettingsContract.State(
            isNotificationsEnabled = false,
            isDarkModeEnabled = false,
            showLogoutConfirmationDialog = false
        )
    }

    override fun handleEvent(event: SettingsContract.Event) {
        when (event) {
            SettingsContract.Event.OnBackClicked ->
                setEffect { SettingsContract.Effect.NavigateBack }

            SettingsContract.Event.OnDarkModeChanged -> {
                appRepository.setIsDarkModeEnabled(!currentState.isDarkModeEnabled)
                setState { copy(isDarkModeEnabled = !isDarkModeEnabled) }
            }

            SettingsContract.Event.OnLogoutConfirmationClicked -> {
                screenModelScope.launch {
                    logoutUseCase()
                    setState { copy(showLogoutConfirmationDialog = !showLogoutConfirmationDialog) }
                }
            }

            SettingsContract.Event.OnLogoutRequestClicked ->
                setState { copy(showLogoutConfirmationDialog = !showLogoutConfirmationDialog) }

            SettingsContract.Event.OnNotificationsChanged -> {
                appRepository.setIsNotificationsEnabled(!currentState.isNotificationsEnabled)
                setState { copy(isNotificationsEnabled = !isNotificationsEnabled) }
            }

            SettingsContract.Event.OnSave -> {
                //todo: remove the save button
                setEffect { SettingsContract.Effect.NavigateBack }
            }

            SettingsContract.Event.OnUpdatePasswordClicked ->
                setEffect { SettingsContract.Effect.NavigateToUpdatePasswordScreen }

            SettingsContract.Event.OnDismissLogoutDialogClicked ->
                setState { copy(showLogoutConfirmationDialog = !showLogoutConfirmationDialog) }

        }
    }

    init {
        screenModelScope.launch {
            val isDarkModeEnabled = appRepository.getIsDarkModeEnabled()
            val isNotificationsEnabled = appRepository.getIsNotificationsEnabled()

            setState {
                copy(
                    isDarkModeEnabled = isDarkModeEnabled,
                    isNotificationsEnabled = isNotificationsEnabled
                )
            }
        }
    }

}
