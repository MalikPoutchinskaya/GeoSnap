package com.kayakstudio.geosnap.ui.features.login.checkemail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.features.login.AuthContract
import com.kayakstudio.geosnap.ui.features.login.common.SimpleCommonView

@Composable
fun CheckEmailView(
    state: AuthContract.State,
    onEvent: (event: AuthContract.Event) -> Unit
) {

    SimpleCommonView(
        title = stringResource(R.string.checkEmailScreen_main_title),
        description = stringResource(
            R.string.checkEmailScreen_main_description,
            state.userFirstName
        ),
        isLoading = state.isLoading,
        firstButtonText = stringResource(R.string.checkEmailScreen_actions_firstButton),
        secondButtonText = stringResource(R.string.checkEmailScreen_actions_secondButton),
        thirdLabelText = stringResource(R.string.checkEmailScreen_notification_thirdLabel),
        thirdTextButtonText = stringResource(R.string.checkEmailScreen_notification_thirdButton),
        onFirstButtonClicked = { onEvent(AuthContract.Event.OnUserOpenEmail) },
        onSecondButtonClicked = { onEvent(AuthContract.Event.OnUserNavigateToLogin) },
        onThirdButtonClicked = { onEvent(AuthContract.Event.OnUserResendForgotPassword) }
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.checkemail_image),
            contentDescription = "check_email"
        )
    }
}