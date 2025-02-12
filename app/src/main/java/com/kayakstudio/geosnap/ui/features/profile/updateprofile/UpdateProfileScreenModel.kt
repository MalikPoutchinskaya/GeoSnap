package com.kayakstudio.geosnap.ui.features.profile.updateprofile

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.document.DocumentRepository
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.tools.extensions.safeMessage
import com.kayakstudio.geosnap.ui.design.components.ccp.AccountPhoneNumber
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class UpdateProfileScreenModel(
    private val userRepository: UserRepository,
    private val documentRepository: DocumentRepository,
) : MviScreenModel<UpdateProfileContract.Event, UpdateProfileContract.State, UpdateProfileContract.Effect>() {

    override fun createInitialState(): UpdateProfileContract.State {
        return UpdateProfileContract.State(
            firstName = "",
            lastName = "",
            email = "",
            gender = "",
            dateOfBirth = "",
            accountPhoneNumber = AccountPhoneNumber.init(),
            isLoading = false,
            showImagePicturePicker = false,
            imgByteArray = null,
            takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.FORM,
            imageUrl = null,
        )
    }

    override fun handleEvent(event: UpdateProfileContract.Event) {
        when (event) {
            UpdateProfileContract.Event.OnBackClicked ->
                setEffect { UpdateProfileContract.Effect.NavigateBack }

            UpdateProfileContract.Event.OnSave ->
                onSave()

            is UpdateProfileContract.Event.OnAccountPhoneNumberChanged ->
                setState { copy(accountPhoneNumber = event.accountPhoneNumber) }

            is UpdateProfileContract.Event.OnDateOfBirthChanged ->
                setState { copy(dateOfBirth = event.dateOfBirth) }

            is UpdateProfileContract.Event.OnEmailChanged ->
                setState { copy(email = event.userEmail) }

            is UpdateProfileContract.Event.OnFirstNameChanged ->
                setState { copy(firstName = event.userFirstName) }

            is UpdateProfileContract.Event.OnGenderChanged ->
                setState { copy(gender = event.gender) }

            is UpdateProfileContract.Event.OnLastNameChanged ->
                setState { copy(lastName = event.userLastName) }

            UpdateProfileContract.Event.OnCameraClicked ->
                setState {
                    copy(
                        showImagePicturePicker = false,
                        takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.CAMERA
                    )
                }

            UpdateProfileContract.Event.OnGalleryPickerClicked ->
                setState {
                    copy(
                        showImagePicturePicker = false,
                        takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.GALLERY
                    )
                }

            UpdateProfileContract.Event.OnImageClicked ->
                setState { copy(showImagePicturePicker = true) }

            UpdateProfileContract.Event.OnDismissImagePickerClicked ->
                setState { copy(showImagePicturePicker = true) }

            UpdateProfileContract.Event.OnDismissCameraClicked ->
                setState { copy(takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.FORM) }

            UpdateProfileContract.Event.OnDismissGalleryClicked ->
                setState { copy(takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.FORM) }

            is UpdateProfileContract.Event.OnImageCaptured ->
                setState {
                    copy(
                        imgByteArray = event.img,
                        takePictureScreenSwitchEnum = TakePictureScreenSwitchEnum.FORM
                    )
                }
        }
    }


    init {
        screenModelScope.launch {
            val user = userRepository.getUserOrThrow()
            setState {
                copy(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    imageUrl = user.imageUrl,
                    gender = "",
                    dateOfBirth = "",
                    accountPhoneNumber = AccountPhoneNumber.init(user.phoneNumber),
                )
            }
        }
    }

    /**
     *
     */
    private fun onSave() {
        screenModelScope.launch {
            setState { copy(isLoading = true) }
            currentState.imgByteArray?.let { imgByteArray ->
                val user = userRepository.getUserOrThrow()
                documentRepository.postProfilePicture(
                    userId = user.id,
                    img = imgByteArray
                ).onSuccess {
                    userRepository.updateProfileImgLocally(it)
                    setState { copy(isLoading = false, imageUrl = it) }
                    setEffect { UpdateProfileContract.Effect.NavigateBack }
                }.onFailure {
                    setState { copy(isLoading = false) }
                    setEffect { UpdateProfileContract.Effect.ShowErrorMessage(it.safeMessage()) }
                }
            }
        }
    }
}
