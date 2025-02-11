package com.kayakstudio.geosnap.ui.features.main

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
) : MviScreenModel<MainContract.Event, MainContract.State, MainContract.Effect>() {
    override fun createInitialState(): MainContract.State {
        return MainContract.State(
            isCameraOn = false,
        )
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            MainContract.Event.OnCameraClicked ->
                setEffect { MainContract.Effect.NavigateToCamera }
        }
    }

    init {
        screenModelScope.launch {

        }
    }
}
