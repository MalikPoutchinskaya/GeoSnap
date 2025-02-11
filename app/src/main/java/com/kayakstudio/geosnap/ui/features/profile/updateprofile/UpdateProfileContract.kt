package com.kayakstudio.geosnap.ui.features.profile.updateprofile

import com.kayakstudio.geosnap.ui.design.components.ccp.AccountPhoneNumber
import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class UpdateProfileContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
    }

    sealed class Event : UiEvent {
        data object OnBackClicked : Event()
        data object OnImageClicked : Event()
        data object OnDismissGalleryClicked : Event()
        data object OnDismissCameraClicked : Event()
        data object OnDismissImagePickerClicked : Event()
        data object OnGalleryPickerClicked : Event()
        data object OnCameraClicked : Event()
        data class OnImageCaptured(val img: ByteArray) : Event()
        data class OnEmailChanged(val userEmail: String) : Event()
        data class OnFirstNameChanged(val userFirstName: String) : Event()
        data class OnLastNameChanged(val userLastName: String) : Event()
        data class OnGenderChanged(val gender: String) : Event()
        data class OnDateOfBirthChanged(val dateOfBirth: String) : Event()
        data class OnAccountPhoneNumberChanged(val accountPhoneNumber: AccountPhoneNumber) : Event()

        data object OnSave : Event()
    }

    data class State(
        val imgByteArray: ByteArray?,
        val imageUrl: String?,
        val firstName: String,
        val lastName: String,
        val email: String,
        val gender: String,
        val dateOfBirth: String,
        val accountPhoneNumber: AccountPhoneNumber,
        val isLoading: Boolean,
        val showImagePicturePicker: Boolean,
        val takePictureScreenSwitchEnum: TakePictureScreenSwitchEnum,
    ) : UiState
}

enum class TakePictureScreenSwitchEnum {
    FORM,
    GALLERY,
    CAMERA
}
