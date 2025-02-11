package com.kayakstudio.geosnap.ui.features.router

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class RouterContract {

    sealed class Effect : UiEffect {
        data object NavigateToHomeScreen : Effect()
        data object NavigateToLoginScreen : Effect()
    }

    sealed class Event : UiEvent

    data object State : UiState
}
