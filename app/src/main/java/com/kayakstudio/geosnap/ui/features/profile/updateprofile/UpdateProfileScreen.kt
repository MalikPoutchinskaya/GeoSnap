package com.kayakstudio.geosnap.ui.features.profile.updateprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.BottomSheets
import com.kayakstudio.geosnap.ui.design.components.Pictures
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.TextFields
import com.kayakstudio.geosnap.ui.design.components.ccp.CountryPickerView
import com.kayakstudio.geosnap.ui.design.components.ccp.getCountriesList
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.design.templates.Templates
import com.preat.peekaboo.image.picker.toImageBitmap
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent


object UpdateProfileScreen : Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val screenModel: UpdateProfileScreenModel = getScreenModel<UpdateProfileScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is UpdateProfileContract.Effect.NavigateBack ->
                        navigator.pop()

                    is UpdateProfileContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        UpdateProfileContent(state, sheetState, scope) { screenModel.setEvent(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileContent(
    state: UpdateProfileContract.State,
    sheetState: SheetState,
    scope: CoroutineScope,
    onEvent: (UpdateProfileContract.Event) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    when (state.takePictureScreenSwitchEnum) {
        TakePictureScreenSwitchEnum.FORM -> {
            Scaffolds.SafePadding(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    AppBars.Top(
                        scrollBehavior = scrollBehavior,
                        title = "Update Profile"
                    ) { onEvent(UpdateProfileContract.Event.OnBackClicked) }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BadgedBox(
                            modifier = Modifier.clickable { onEvent(UpdateProfileContract.Event.OnImageClicked) },
                            badge = {
                                Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                                    Icon(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(2.dp),
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit"
                                    )
                                }
                            }
                        ) {
                            if (state.imgByteArray != null) {
                                Pictures.Avatar(imageBitmap = state.imgByteArray.toImageBitmap())
                            } else {
                                Pictures.Avatar(asyncPainterResource(data = state.imageUrl ?: ""))
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                            TextFields.TextField(
                                modifier = Modifier.weight(1f),
                                value = state.firstName,
                                onValueChange = {
                                    onEvent(
                                        UpdateProfileContract.Event.OnFirstNameChanged(
                                            it
                                        )
                                    )
                                },
                                label = stringResource(R.string.signupScreen_form_firstNameLabel),
                                hint = stringResource(R.string.signupScreen_form_firstNameHint),
                            )

                            TextFields.TextField(
                                modifier = Modifier.weight(1f),
                                value = state.lastName,
                                onValueChange = {
                                    onEvent(
                                        UpdateProfileContract.Event.OnLastNameChanged(
                                            it
                                        )
                                    )
                                },
                                label = stringResource(R.string.signupScreen_form_lastNameLabel),
                                hint = stringResource(R.string.signupScreen_form_lastNameHint),
                            )
                        }
                        TextFields.TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.dateOfBirth,
                            onValueChange = {
                                onEvent(UpdateProfileContract.Event.OnDateOfBirthChanged(it))
                            },
                            label = "Date of birth",
                            hint = "30 0ctober 1990",
                        )
                        GenderSelection(selectedGender = state.gender) {
                            onEvent(UpdateProfileContract.Event.OnGenderChanged(it))
                        }
                        TextFields.TextField(
                            value = state.email,
                            onValueChange = {
                                onEvent(
                                    UpdateProfileContract.Event.OnEmailChanged(it)
                                )
                            },
                            label = stringResource(R.string.signupScreen_form_emailLabel),
                            hint = stringResource(R.string.signupScreen_form_emailHint),
                            modifier = Modifier.fillMaxWidth()
                        )
                        TextFields.TextField(
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            label = stringResource(R.string.signupScreen_form_phoneNumberLabel),
                            hint = stringResource(R.string.signupScreen_form_phoneNumberHint),
                            value = state.accountPhoneNumber.formatNationalPhoneNumber(),
                            leadingIcon = {
                                CountryPickerView(
                                    countries = getCountriesList(),
                                    selectedCountry = state.accountPhoneNumber.countryCode,
                                    isEnabled = false,
                                    textColor = Color.Unspecified,
                                    onSelection = { selectedCountry ->
                                        onEvent(
                                            UpdateProfileContract.Event.OnAccountPhoneNumberChanged(
                                                accountPhoneNumber = state.accountPhoneNumber.copy(
                                                    countryCode = selectedCountry
                                                ),
                                            )
                                        )
                                    },
                                )
                            },
                            onValueChange = { newValue ->
                                onEvent(
                                    UpdateProfileContract.Event.OnAccountPhoneNumberChanged(
                                        accountPhoneNumber = state.accountPhoneNumber.copy(
                                            phoneNumber = newValue
                                        ),
                                    )
                                )
                            },
                        )
                    }
                },
                bottomBar = {
                    AppBars.Bottom(
                        firstButtonText = "SAVE",
                        onFirstButtonClicked = { onEvent(UpdateProfileContract.Event.OnSave) },
                    )
                }
            )

            if (state.showImagePicturePicker) {
                BottomSheets.Default(
                    sheetState = sheetState,
                    scope = scope,
                    title = stringResource(R.string.updateProfileScreen_setPictureSheet_title),
                    content = stringResource(R.string.updateProfileScreen_setPictureSheet_content),
                    positiveButtonLabel = stringResource(R.string.updateProfileScreen_setPictureSheet_gallery),
                    positiveButtonPainter = rememberVectorPainter(Icons.Default.Collections),
                    negativeButtonLabel = stringResource(R.string.updateProfileScreen_setPictureSheet_camera),
                    negativeButtonPainter = rememberVectorPainter(Icons.Default.Camera),
                    onDismissed = {
                        onEvent(UpdateProfileContract.Event.OnDismissImagePickerClicked)
                    },
                    onPositiveClicked = {
                        onEvent(UpdateProfileContract.Event.OnGalleryPickerClicked)
                    },
                    onNegativeClicked = {
                        onEvent(UpdateProfileContract.Event.OnCameraClicked)
                    },
                )
            }
        }

        TakePictureScreenSwitchEnum.GALLERY,
        TakePictureScreenSwitchEnum.CAMERA -> {
            Templates.Error("Beat feature", "This feature will be release soon")
        }
    }

}

@Composable
fun GenderSelection(selectedGender: String, onSelectionChanged: (label: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = stringResource(R.string.updateProfileScreen_genderSelection_title), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            RadioButtonWithLabel(
                label = stringResource(R.string.updateProfileScreen_genderSelection_male),
                selected = selectedGender == "Male",
                modifier = Modifier.weight(1f)
            ) { onSelectionChanged("Male") }

            RadioButtonWithLabel(
                label = stringResource(R.string.updateProfileScreen_genderSelection_female),
                selected = selectedGender == "Female",
                modifier = Modifier.weight(1f)
            ) { onSelectionChanged("Female") }
        }
    }
}


@Composable
fun RadioButtonWithLabel(
    label: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .clickable { onClick() },
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label)
    }
}