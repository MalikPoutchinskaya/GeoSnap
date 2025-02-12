package com.kayakstudio.geosnap.ui.features.profile.aboutme

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.TextFields
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent

object AboutMeScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: AboutMeScreenModel = getScreenModel<AboutMeScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is AboutMeContract.Effect.NavigateBack ->
                        navigator.pop()

                    is AboutMeContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        AboutMeContent(state) { screenModel.setEvent(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutMeContent(
    state: AboutMeContract.State,
    onEvent: (AboutMeContract.Event) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffolds.SafePadding(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBars.Top(
                scrollBehavior = scrollBehavior,
                title = stringResource(R.string.aboutMeScreen_title)
            ) { onEvent(AboutMeContract.Event.OnBackClicked) }
        },
        content = {
            TextFields.TextField(
                modifier = Modifier.height(335.dp),
                value = state.aboutMe,
                label = "",
                hint = stringResource(R.string.aboutMeScreen_hint),
                maxLines = 10,
            ) {
                onEvent(AboutMeContract.Event.OnAboutMeChanged(it))
            }
        },
        bottomBar = {
            AppBars.Bottom(
                firstButtonText = stringResource(R.string.aboutMeScreen_save_button),
                isLoading = state.isLoading,
                onFirstButtonClicked = { onEvent(AboutMeContract.Event.OnSave) },
            )
        }
    )
}
