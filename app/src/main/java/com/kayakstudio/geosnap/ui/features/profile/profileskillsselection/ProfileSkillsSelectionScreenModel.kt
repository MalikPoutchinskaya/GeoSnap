package com.kayakstudio.geosnap.ui.features.profile.profileskillsselection

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class ProfileSkillsSelectionScreenModel(
    private val type: ProfileSkillSelectionType,
    private val userRepository: UserRepository,
) : MviScreenModel<ProfileSkillSelectionContract.Event, ProfileSkillSelectionContract.State, ProfileSkillSelectionContract.Effect>() {

    override fun createInitialState(): ProfileSkillSelectionContract.State {
        return ProfileSkillSelectionContract.State(
            chips = listOf(),
            type = ProfileSkillSelectionType.TARGET_JOBS
        )
    }

    override fun handleEvent(event: ProfileSkillSelectionContract.Event) {
        when (event) {
            ProfileSkillSelectionContract.Event.OnSave ->
                onSave()

            ProfileSkillSelectionContract.Event.OnBackClicked ->
                setEffect { ProfileSkillSelectionContract.Effect.NavigateBack }

            is ProfileSkillSelectionContract.Event.OnChipsClicked -> {
                onChipsClicked(event)
            }
        }
    }


    init {
        screenModelScope.launch {
            val chips = FakeData.getChips(type)
            setState { copy(type = type, chips = chips) }
        }
    }

    /**
     *
     */
    private fun onSave() {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
//            val aboutMe = currentState.aboutMe
//            if (aboutMe.isNotBlank()) {
//                userRepository.updateAboutMeLocally(aboutMe)
//                setState { copy(isLoading = false) }
            setEffect { ProfileSkillSelectionContract.Effect.NavigateBack }
//            }
        }
    }

    /**
     *
     */
    private fun onChipsClicked(event: ProfileSkillSelectionContract.Event.OnChipsClicked) {
        val newChipId = event.chipId
        val currentIds = ArrayList(currentState.selectedChips)
        if (currentIds.contains(event.chipId)) {
            currentIds.remove(newChipId)
        } else {
            currentIds.add(newChipId)
        }
        setState { copy(selectedChips = currentIds) }
    }

}
