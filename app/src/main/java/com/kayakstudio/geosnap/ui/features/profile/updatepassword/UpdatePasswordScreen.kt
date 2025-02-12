package com.kayakstudio.geosnap.ui.features.profile.updatepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.TextFields
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
                title = "Update Password"
            ) { onEvent(UpdatePasswordContract.Event.OnNavigateBack) }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PasswordField("Old Password", state.old1) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(1, it))
                }
                PasswordField("New Password", state.old2) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(2, it))
                }
                PasswordField("Confirm Password", state.new) {
                    onEvent(UpdatePasswordContract.Event.OnPasswordChanged(3, it))
                }
            }
        },
        bottomBar = {
            AppBars.Bottom(
                firstButtonText = "UPDATE",
                onFirstButtonClicked = { onEvent(UpdatePasswordContract.Event.OnNavigateBack) },
            )
        }
    )
}