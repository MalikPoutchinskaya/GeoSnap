package com.kayakstudio.geosnap.ui.features.profile.updateprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.kayakstudio.geosnap.ui.design.components.Pictures
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.TextFields
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
                        title = stringResource(R.string.updateProfileScreen_title)
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
                                        contentDescription = stringResource(R.string.updateProfileScreen_edit_image)
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

                        TextFields.TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.dateOfBirth,
                            onValueChange = {
                                onEvent(UpdateProfileContract.Event.OnDateOfBirthChanged(it))
                            },
                            label = stringResource(R.string.updateProfileScreen_dob_label),
                            hint = stringResource(R.string.updateProfileScreen_dob_hint),
                        )
                    }
                },
                bottomBar = {
                    AppBars.Bottom(
                        firstButtonText = stringResource(R.string.updateProfileScreen_save_button),
                        onFirstButtonClicked = { onEvent(UpdateProfileContract.Event.OnSave) },
                    )
                }
            )
        }

        TakePictureScreenSwitchEnum.GALLERY,
        TakePictureScreenSwitchEnum.CAMERA -> {
            Templates.Error("Beta feature", "This feature is not implemented yet.")
        }
    }
}
