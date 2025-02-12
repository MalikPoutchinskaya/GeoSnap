package com.kayakstudio.geosnap.ui.features.profile.updatepassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.*
import com.kayakstudio.geosnap.ui.design.components.TextFields.PasswordField
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent

object UpdatePasswordScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: UpdatePasswordScreenModel = getScreenModel<UpdatePasswordScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is UpdatePasswordContract.Effect.NavigateBack ->
                        navigator.pop()

                    is UpdatePasswordContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        UpdatePasswordContent(state) { screenModel.setEvent(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordContent(
    state: UpdatePasswordContract.State,
    onEvent: (UpdatePasswordContract.Event) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffolds.SafePadding(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBars.Top(
                scrollBehavior = scrollBehavior,
                title = stringResource(R.string.updatePasswordScreen_title)
            ) { onEvent(UpdatePasswordContract.Event.OnNavigateBack) }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PasswordField(stringResource(R.string.updatePasswordScreen_old_password), state.old1) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(1, it))
                }
                PasswordField(stringResource(R.string.updatePasswordScreen_new_password), state.old2) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(2, it))
                }
                PasswordField(stringResource(R.string.updatePasswordScreen_confirm_password), state.new) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(3, it))
                }
            }
        },
        bottomBar = {
            AppBars.Bottom(
                firstButtonText = stringResource(R.string.updatePasswordScreen_update_button),
                onFirstButtonClicked = { onEvent(UpdatePasswordContract.Event.OnNavigateBack) },
            )
        }
    )
}