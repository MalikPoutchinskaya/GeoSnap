package com.kayakstudio.geosnap.ui.features.profile.profileskillsselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf


data class ProfileSkillsSelectionScreen(private val type: ProfileSkillSelectionType) : Screen,
    KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: ProfileSkillsSelectionScreenModel =
            getScreenModel<ProfileSkillsSelectionScreenModel> { parametersOf(type) }
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is ProfileSkillSelectionContract.Effect.NavigateBack ->
                        navigator.pop()

                    is ProfileSkillSelectionContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )
                }
            }
        }

        LanguagesContent(state) { screenModel.setEvent(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun LanguagesContent(
    state: ProfileSkillSelectionContract.State,
    onEvent: (ProfileSkillSelectionContract.Event) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val title = when (state.type) {
        ProfileSkillSelectionType.TARGET_JOBS -> "Target Jobs"
        ProfileSkillSelectionType.LANGUAGES -> "Languages"
        ProfileSkillSelectionType.SOFTWARE -> "Software"
        ProfileSkillSelectionType.SERVICES -> "Services"
    }
    Scaffolds.SafePadding(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBars.Top(
                scrollBehavior = scrollBehavior,
                title = title
            ) { onEvent(ProfileSkillSelectionContract.Event.OnBackClicked) }
        },
        content = {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                state.chips.forEach { chip ->
                    FilterChip(
                        selected = state.selectedChips.contains(chip),
                        onClick = { onEvent(ProfileSkillSelectionContract.Event.OnChipsClicked(chip)) },
                        label = { Text(chip) },
                    )
                }
            }
        },
        bottomBar = {
            AppBars.Bottom(
                firstButtonText = "SAVE",
                isLoading = state.isLoading,
                onFirstButtonClicked = { onEvent(ProfileSkillSelectionContract.Event.OnSave) },
            )
        }
    )
}