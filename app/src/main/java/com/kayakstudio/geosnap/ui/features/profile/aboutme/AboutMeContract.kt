package com.kayakstudio.geosnap.ui.features.profile.aboutme

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class AboutMeContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
    }

    sealed class Event : UiEvent {
        data object OnBackClicked : Event()
        data class OnAboutMeChanged(val content: String) : Event()
        data object OnSave : Event()
    }

    data class State(
        val aboutMe: String = "",
        val isLoading: Boolean = false,
    ) : UiState
}