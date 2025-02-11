package com.kayakstudio.geosnap.ui.features.main

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class MainContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateToCamera : Effect()
    }

    sealed class Event : UiEvent {
        data object OnCameraClicked : Event()
    }

    data class State(
        val isCameraOn: Boolean,
    ) : UiState
}