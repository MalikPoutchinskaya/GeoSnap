package com.kayakstudio.geosnap.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.data.user.UserModel
import com.kayakstudio.geosnap.ui.design.components.Pictures
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.showErrorSnackbar
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import com.kayakstudio.geosnap.ui.features.profile.aboutme.AboutMeScreen
import com.kayakstudio.geosnap.ui.features.profile.settings.SettingsScreen
import com.kayakstudio.geosnap.ui.features.profile.updateprofile.UpdateProfileScreen
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import androidx.compose.ui.platform.LocalContext as LocalContext1


object ProfileScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val screenModel: ProfileScreenModel = getScreenModel<ProfileScreenModel>()
        val state by screenModel.uiState.collectAsState()
        val context = LocalContext1.current

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    is ProfileContract.Effect.ShowErrorMessage ->
                        scope.showErrorSnackbar(
                            title = context.getString(R.string.defaultSnackbar_title_error),
                            description = effect.message
                        )

                    ProfileContract.Effect.NavigateToSettingsScreen ->
                        navigator.push(SettingsScreen)

                    ProfileContract.Effect.NavigateToUpdateProfileScreen ->
                        navigator.push(UpdateProfileScreen)

                    ProfileContract.Effect.NavigateToAboutMeScreen ->
                        navigator.push(AboutMeScreen)
                }
            }
        }

        ProfileScreenContent(state) { screenModel.setEvent(it) }
    }
}

@Composable
fun ProfileScreenContent(
    state: ProfileContract.State,
    onEvent: (ProfileContract.Event) -> Unit,
) {
    state.user?.let { user ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 64.dp)
        ) {
            // HEADER
            item {
                ProfileHeader(
                    user = user,
                    onSettingsClicked = { onEvent(ProfileContract.Event.OnSettingsClicked) },
                    onEditClicked = { onEvent(ProfileContract.Event.OnEditProfileClicked) }
                )
            }

            // ABOUT
            item {
                val content: (@Composable () -> Unit)? = if (user.aboutMe.isBlank()) null else {
                    { Text(user.aboutMe, fontSize = 14.sp) }
                }
                ProfileSection(
                    title = stringResource(R.string.profileScreen_about_me),
                    icon = Icons.Outlined.Person,
                    isIncremental = false,
                    onActionClicked = { onEvent(ProfileContract.Event.OnAboutMeClicked) },
                    content = content
                )
            }

        }
    }

}

@Composable
fun ProfileHeader(
    user: UserModel,
    onSettingsClicked: () -> Unit,
    onEditClicked: () -> Unit
) {
    val bgBrushedColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
    )
    val gradient = Brush.horizontalGradient(bgBrushedColors)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = gradient,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .systemBarsPadding()
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { onSettingsClicked() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.profileScreen_settings),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
        Pictures.Avatar(asyncPainterResource(data = user.imageUrl ?: ""))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            user.displayName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text("Nantes, France", fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileStat(title = user.points.toString(), subtitle = stringResource(R.string.profileScreen_label_earned))
            ProfileStat(title = "78", subtitle = stringResource(R.string.profileScreen_label_geoguess))

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onEditClicked,
                colors = ButtonDefaults.filledTonalButtonColors()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.profileScreen_button_editProfile))
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Edit")
                }
            }
        }
    }
}

@Composable
fun ProfileStat(title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
        Spacer(Modifier.width(4.dp))
        Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun ProfileSection(
    title: String,
    icon: ImageVector,
    isIncremental: Boolean = true,
    isActionEnable: Boolean = true,
    onActionClicked: () -> Unit,
    content: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.horizontalScreenPadding)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, "leading icon", tint = MaterialTheme.colorScheme.tertiaryContainer)
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onActionClicked, enabled = isActionEnable) {
                if (isIncremental) {
                    Icon(Icons.Default.AddCircle, "Add", tint = MaterialTheme.colorScheme.secondary)
                } else {
                    Icon(Icons.Outlined.Edit, "Edit", tint = MaterialTheme.colorScheme.secondary)
                }
            }
        }
        if (content != null) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            content()
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}