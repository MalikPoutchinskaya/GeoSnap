package com.kayakstudio.geosnap.ui.features.profile

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class ProfileScreenModel(
    private val userRepository: UserRepository,
) : MviScreenModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    override fun createInitialState(): ProfileContract.State {
        return ProfileContract.State(
            user = null
        )
    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            ProfileContract.Event.OnEditProfileClicked ->
                setEffect { ProfileContract.Effect.NavigateToUpdateProfileScreen }

            ProfileContract.Event.OnSettingsClicked ->
                setEffect { ProfileContract.Effect.NavigateToSettingsScreen }

            ProfileContract.Event.OnAboutMeClicked ->
                setEffect { ProfileContract.Effect.NavigateToAboutMeScreen }
        }
    }

    init {
        observeUserProfile()
    }

    /**
     *
     */
    private fun observeUserProfile() {
        screenModelScope.launch {
            userRepository.observeUser().collect { user ->
                setState { copy(user = user) }
            }
        }
    }
}
