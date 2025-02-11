package com.kayakstudio.geosnap.ui.features.login.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.TextFields
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.common.SimpleCommonView

@Composable
fun LoginView(
    state: AuthContract.State,
    onEvent: (event: AuthContract.Event) -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    SimpleCommonView(
        title = stringResource(R.string.loginScreen_main_title),
        description = stringResource(R.string.loginScreen_main_description),
        isLoading = state.isLoading,
        firstButtonText = stringResource(R.string.loginScreen_actions_firstButton),
        secondButtonText = stringResource(R.string.loginScreen_actions_secondButton),
        secondButtonPainter = painterResource(R.drawable.ic_google_logo),
        thirdLabelText = stringResource(R.string.loginScreen_footer_thirdLabel),
        thirdTextButtonText = stringResource(R.string.loginScreen_footer_thirdButton),
        onFirstButtonClicked = { onEvent(AuthContract.Event.OnUserLogin) },
        onSecondButtonClicked = {},
        onThirdButtonClicked = { onEvent(AuthContract.Event.OnUserNavigateToSignup) }
    ) {
        TextFields.TextField(
            value = state.userEmail,
            onValueChange = { onEvent(AuthContract.Event.OnEmailChanged(it)) },
            label = stringResource(R.string.loginScreen_form_emailLabel),
            hint = stringResource(R.string.loginScreen_form_emailHint),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        TextFields.TextField(
            value = state.password,
            onValueChange = { onEvent(AuthContract.Event.OnPasswordChanged(it)) },
            label = stringResource(R.string.loginScreen_form_passwordLabel),
            hint = stringResource(R.string.loginScreen_form_passwordHint),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
        )
        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = { onEvent(AuthContract.Event.OnUserNavigateToForgotPassword) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.loginScreen_actions_forgotPassword), color = Color.Gray)
        }
    }
}