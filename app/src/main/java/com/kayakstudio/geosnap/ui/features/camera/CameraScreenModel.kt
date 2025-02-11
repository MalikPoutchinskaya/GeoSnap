package com.kayakstudio.geosnap.ui.features.camera

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.document.DocumentRepository
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.tools.extensions.safeMessage
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraScreenModel(
    private val documentRepository: DocumentRepository,
    private val userRepository: UserRepository
) :
    MviScreenModel<CameraContract.Event, CameraContract.State, CameraContract.Effect>() {
    override fun createInitialState(): CameraContract.State =
        CameraContract.State(
            pictureUrl = "",
            img = null,
            isCapturing = false,
            isSending = false,
        )

    override fun handleEvent(event: CameraContract.Event) {
        when (event) {
            CameraContract.Event.OnUserClickOnLaunchCamera -> {
                setState { copy(isCapturing = false) }
                setEffect { CameraContract.Effect.NavigateToCameraScreen }
            }

            CameraContract.Event.OnUserClickOnPhotoCapture ->
                setState { copy(isCapturing = true) }

            CameraContract.Event.OnUserClickOnBrowseGallery ->
                setEffect { CameraContract.Effect.OpenGallery }

            is CameraContract.Event.OnPhotoCaptured ->
                onPhotoCaptured(event.img)

            CameraContract.Event.OnUserClickOnPhotoClose ->
                setEffect { CameraContract.Effect.NavigateBack }

            CameraContract.Event.OnUserClickOnBack ->
                setEffect { CameraContract.Effect.NavigateBack }

            CameraContract.Event.OnUserValidatePhoto ->
                onPhotoValidated()
        }
    }

    /**
     *
     */
    private val exceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            val errorMessage = exception.message ?: "An error occurred"
            setEffect { CameraContract.Effect.ShowErrorMessage(errorMessage) }
        }

    /**
     *
     */
    private fun onPhotoCaptured(img: ByteArray) {
        setState { copy(img = img, isCapturing = false) }
        setEffect { CameraContract.Effect.NavigateToGeoGuessValidationScreen }
    }

    /**
     *
     */
    private fun onPhotoValidated() {
        screenModelScope.launch(exceptionHandler) {
            setState { copy(isSending = true) }
            val user = userRepository.getUserOrThrow()
            currentState.img?.let {
                documentRepository.postGeoGuessPicture(userId = user.id, img = it)
                    .onSuccess {
                        setState { copy(isSending = false) }
                        setEffect { CameraContract.Effect.ShowPhotoSentMessage }
                        delay(2000)
                        setEffect { CameraContract.Effect.NavigateBack }
                    }
                    .onFailure {
                        setState { copy(isSending = false) }
                        setEffect { CameraContract.Effect.ShowErrorMessage(it.safeMessage()) }
                    }
            }
        }

    }
}