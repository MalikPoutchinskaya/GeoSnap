package com.kayakstudio.geosnap.ui.features.profile.aboutme

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class AboutMeScreenModel(
    private val userRepository: UserRepository,
) : MviScreenModel<AboutMeContract.Event, AboutMeContract.State, AboutMeContract.Effect>() {

    override fun createInitialState(): AboutMeContract.State {
        return AboutMeContract.State()
    }

    override fun handleEvent(event: AboutMeContract.Event) {
        when (event) {
            AboutMeContract.Event.OnSave ->
                onSave()

            is AboutMeContract.Event.OnAboutMeChanged ->
                setState { copy(aboutMe = event.content) }

            AboutMeContract.Event.OnBackClicked ->
                setEffect { AboutMeContract.Effect.NavigateBack }
        }
    }


    init {
        screenModelScope.launch {
            val user = userRepository.getUserOrThrow()
            setState { copy(aboutMe = user.aboutMe) }
        }
    }

    /**
     *
     */
    private fun onSave() {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
            val aboutMe = currentState.aboutMe
            if (aboutMe.isNotBlank()) {
                userRepository.updateAboutMeLocally(aboutMe)
                setState { copy(isLoading = false) }
                setEffect { AboutMeContract.Effect.NavigateBack }
            }
        }
    }
}