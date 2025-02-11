package com.kayakstudio.geosnap.ui.features.profile

import com.kayakstudio.geosnap.data.user.UserModel
import com.kayakstudio.geosnap.ui.features.profile.profileskillsselection.ProfileSkillSelectionType
import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState
import io.github.vinceglb.filekit.core.PlatformFile

/**
 *
 */
class ProfileContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateToSettingsScreen : Effect()
        data object NavigateToUpdateProfileScreen : Effect()
        data object NavigateToAboutMeScreen : Effect()
    }

    sealed class Event : UiEvent {
        data object OnSettingsClicked : Event()
        data object OnEditProfileClicked : Event()
        data object OnAboutMeClicked : Event()
    }

    data class State(
        val user: UserModel? = null,
    ) : UiState
}
