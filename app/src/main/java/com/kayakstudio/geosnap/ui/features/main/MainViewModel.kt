package com.kayakstudio.geosnap.ui.features.main

import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel

class MainViewModel :
    MviScreenModel<MainContract.Event, MainContract.State, MainContract.Effect>() {
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
}
