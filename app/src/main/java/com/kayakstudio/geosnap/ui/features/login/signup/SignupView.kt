package com.kayakstudio.geosnap.ui.features.login.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.TextFields
import com.kayakstudio.geosnap.ui.design.components.TextFields.PasswordField
import com.kayakstudio.geosnap.ui.design.components.ccp.CountryPickerView
import com.kayakstudio.geosnap.ui.design.components.ccp.getCountriesList
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.common.SimpleCommonView

@Composable
fun SignupView(
    state: AuthContract.State,
    onEvent: (event: AuthContract.Event) -> Unit
) {
    val heightSpacer = 24.dp

    SimpleCommonView(
        title = stringResource(R.string.signupScreen_main_title),
        description = stringResource(R.string.signupScreen_main_description),
        isLoading = state.isLoading,
        firstButtonText = stringResource(R.string.signupScreen_actions_firstButton),
        secondButtonText = stringResource(R.string.signupScreen_actions_secondButton),
        secondButtonPainter = painterResource(R.drawable.ic_google_logo),
        onFirstButtonClicked = { onEvent(AuthContract.Event.OnUserSignup) },
        onSecondButtonClicked = {},
        onNavigateBack = { onEvent(AuthContract.Event.OnUserNavigateToLogin) }
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            TextFields.TextField(
                modifier = Modifier.weight(1f),
                value = state.userFirstName,
                onValueChange = { onEvent(AuthContract.Event.OnFirstNameChanged(it)) },
                label = stringResource(R.string.signupScreen_form_firstNameLabel),
                hint = stringResource(R.string.signupScreen_form_firstNameHint),
            )

            TextFields.TextField(
                modifier = Modifier.weight(1f),
                value = state.userLastName,
                onValueChange = { onEvent(AuthContract.Event.OnLastNameChanged(it)) },
                label = stringResource(R.string.signupScreen_form_lastNameLabel),
                hint = stringResource(R.string.signupScreen_form_lastNameHint),
            )
        }
        Spacer(Modifier.height(16.dp))
        TextFields.TextField(
            value = state.userEmail,
            onValueChange = { onEvent(AuthContract.Event.OnEmailChanged(it)) },
            label = stringResource(R.string.signupScreen_form_emailLabel),
            hint = stringResource(R.string.signupScreen_form_emailHint),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(heightSpacer))
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
                            AuthContract.Event.OnAccountPhoneNumberChanged(
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
                    AuthContract.Event.OnAccountPhoneNumberChanged(
                        accountPhoneNumber = state.accountPhoneNumber.copy(
                            phoneNumber = newValue
                        ),
                    )
                )
            },
        )
        Spacer(Modifier.height(heightSpacer))
        PasswordField(stringResource(R.string.signupScreen_form_password), state.newPassword) {
            onEvent(AuthContract.Event.OnNewPasswordChanged(it))
        }
    }
}