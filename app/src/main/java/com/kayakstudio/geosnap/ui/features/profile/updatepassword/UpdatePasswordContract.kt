package com.kayakstudio.geosnap.ui.features.profile.updatepassword

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class UpdatePasswordContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
    }

    sealed class Event : UiEvent {
        data object OnNavigateBack : Event()
        data class OnPasswordChanged(val index: Int, val newValue: String) : Event()
        data object OnSave : Event()
    }

    data class State(
        val old1: String,
        val old2: String,
        val new: String
    ) : UiState
}
