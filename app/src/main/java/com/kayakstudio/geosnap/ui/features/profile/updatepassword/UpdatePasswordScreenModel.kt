package com.kayakstudio.geosnap.ui.features.profile.updatepassword

import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel

class UpdatePasswordScreenModel(
    private val userRepository: UserRepository,
) : MviScreenModel<UpdatePasswordContract.Event, UpdatePasswordContract.State, UpdatePasswordContract.Effect>() {

    override fun createInitialState(): UpdatePasswordContract.State {
        return UpdatePasswordContract.State(
            old1 = "", old2 = "", new = ""
        )
    }

    override fun handleEvent(event: UpdatePasswordContract.Event) {
        when (event) {
            UpdatePasswordContract.Event.OnSave -> {
                //todo save the changes
                setEffect { UpdatePasswordContract.Effect.NavigateBack }
            }

            UpdatePasswordContract.Event.OnNavigateBack ->
                setEffect { UpdatePasswordContract.Effect.NavigateBack }

            is UpdatePasswordContract.Event.OnPasswordChanged -> {
                when (event.index) {
                    1 -> setState { copy(old1 = event.newValue) }
                    2 -> setState { copy(old2 = event.newValue) }
                    else -> setState { copy(new = event.newValue) }
                }
            }
        }
    }
}
