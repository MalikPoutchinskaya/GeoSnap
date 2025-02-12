package com.kayakstudio.geosnap.ui.features.camera.frame

import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState


class CameraContract {

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data object NavigateToGeoGuessValidationScreen : Effect()

        data class ShowErrorMessage(val message: String) : Effect()
        data object ShowPhotoSentMessage : Effect()

    }

    sealed class Event : UiEvent {
        data object OnUserClickOnBack : Event()
        data object OnUserClickOnPhotoCapture : Event()
        data object OnUserClickOnPhotoClose : Event()
        data object OnUserValidatePhoto : Event()
        data class OnPhotoCaptured(val img: ByteArray) : Event()
    }

    data class State(
        val img: ByteArray?,
        val isCapturing: Boolean,
        val isSending: Boolean,
    ) : UiState

}
