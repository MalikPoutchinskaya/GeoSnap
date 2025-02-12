package com.kayakstudio.geosnap.ui.features.profile.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.BottomSheets
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.features.profile.updatepassword.UpdatePasswordScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent

object SettingsScreen : Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()
        val screenModel: SettingsScreenModel = getScreenModel<SettingsScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is SettingsContract.Effect.NavigateBack -> navigator.pop()
                    is SettingsContract.Effect.NavigateToUpdatePasswordScreen ->
                        navigator.push(UpdatePasswordScreen)

                    is SettingsContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        if (state.showLogoutConfirmationDialog) {
            BottomSheets.Default(
                sheetState = sheetState,
                scope = scope,
                title = stringResource(R.string.settingsScreen_logout_title),
                content = stringResource(R.string.settingsScreen_logout_description),
                positiveButtonLabel = stringResource(R.string.settingsScreen_logout_confirm),
                negativeButtonLabel = stringResource(R.string.settingsScreen_logout_cancel),
                onDismissed = {
                    screenModel.setEvent(SettingsContract.Event.OnDismissLogoutDialogClicked)
                },
                onPositiveClicked = {
                    screenModel.setEvent(SettingsContract.Event.OnLogoutConfirmationClicked)
                },
                onNegativeClicked = {
                    screenModel.setEvent(SettingsContract.Event.OnDismissLogoutDialogClicked)
                },
            )
        }

        SettingsContent(state) { screenModel.setEvent(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    state: SettingsContract.State,
    onEvent: (SettingsContract.Event) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffolds.SafePadding(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBars.Top(
                scrollBehavior,
                stringResource(R.string.settingsScreen_title)
            ) { onEvent(SettingsContract.Event.OnBackClicked) }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingToggleItem(
                    icon = Icons.Default.Notifications,
                    title = stringResource(R.string.settingsScreen_notifications),
                    toggleState = state.isNotificationsEnabled,
                    onToggleChange = { onEvent(SettingsContract.Event.OnNotificationsChanged) }
                )
                SettingToggleItem(
                    icon = Icons.Default.Brightness6,
                    title = stringResource(R.string.settingsScreen_dark_mode),
                    toggleState = state.isDarkModeEnabled,
                    onToggleChange = { onEvent(SettingsContract.Event.OnDarkModeChanged) }
                )
                SettingNavigationItem(
                    icon = Icons.Default.Password,
                    title = stringResource(R.string.settingsScreen_password),
                    onClick = { onEvent(SettingsContract.Event.OnUpdatePasswordClicked) }
                )
                SettingNavigationItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    title = stringResource(R.string.settingsScreen_logout),
                    onClick = { onEvent(SettingsContract.Event.OnLogoutRequestClicked) }
                )
            }
        },
    )
}


@Composable
fun SettingToggleItem(
    icon: ImageVector,
    title: String,
    toggleState: Boolean,
    onToggleChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp)
        }
        Switch(
            checked = toggleState,
            onCheckedChange = onToggleChange
        )
    }
}

@Composable
fun SettingNavigationItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = title)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 16.sp)
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "ChevronRight",
        )
    }
}
