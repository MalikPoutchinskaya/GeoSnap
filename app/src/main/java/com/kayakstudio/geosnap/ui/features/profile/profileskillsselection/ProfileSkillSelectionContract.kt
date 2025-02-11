package com.kayakstudio.geosnap.ui.features.profile.profileskillsselection

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class ProfileSkillSelectionContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
    }

    sealed class Event : UiEvent {
        data object OnBackClicked : Event()
        data class OnChipsClicked(val chipId: String) : Event()
        data object OnSave : Event()
    }

    data class State(
        val type: ProfileSkillSelectionType,
        val chips: List<String> = listOf(),
        val selectedChips: List<String> = listOf(),
        val isLoading: Boolean = false,
    ) : UiState
}