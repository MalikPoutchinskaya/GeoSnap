package com.kayakstudio.geosnap.ui.features.login.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.TextFields
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.common.SimpleCommonView

@Composable
fun ForgotPasswordView(
    state: AuthContract.State,
    onEvent: (event: AuthContract.Event) -> Unit
) {

    SimpleCommonView(
        title = stringResource(R.string.forgotPasswordScreen_main_title),
        description = stringResource(R.string.forgotPasswordScreen_main_description),
        isLoading = state.isLoading,
        firstButtonText = stringResource(R.string.forgotPasswordScreen_actions_firstButton),
        onFirstButtonClicked = { onEvent(AuthContract.Event.OnUserResetPassword) },
        onNavigateBack = { onEvent(AuthContract.Event.OnUserNavigateToLogin) }
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource( R.drawable.forgotpassword_image),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextFields.TextField(
            value = state.userEmail,
            onValueChange = { onEvent(AuthContract.Event.OnEmailChanged(it)) },
            label = stringResource(R.string.forgotPasswordScreen_form_emailLabel),
            hint = stringResource(R.string.forgotPasswordScreen_form_emailHint),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
    }
}