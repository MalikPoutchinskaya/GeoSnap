package com.kayakstudio.geosnap.ui.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.kayakstudio.geosnap.ui.features.camera.frame.CameraScreen
import kotlinx.coroutines.flow.collectLatest

object MainScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<MainViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            screenModel.effect.collectLatest { effect ->
                when (effect) {
                    MainContract.Effect.NavigateToCamera ->
                        navigator.push(CameraScreen)

                    is MainContract.Effect.ShowErrorMessage -> TODO()
                }
            }
        }

        MainNavigator {
            screenModel.setEvent(MainContract.Event.OnCameraClicked)
        }
    }
}

@Composable
fun MainNavigator(onCameraClicked: () -> Unit) {
    TabNavigator(HomeTab) { tabNavigator ->
        Scaffold(
            content = { scaffoldPadding ->
                Box(modifier = Modifier.padding(bottom = scaffoldPadding.calculateBottomPadding())) {
                    CurrentTab()
                }
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(GeoGuesserTab)
                        TabNavigationItem(ProfileTab)
                        Spacer(Modifier.weight(1f))
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { onCameraClicked() },
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "PhotoCamera"
                            )
                        }
                    }
                )
            },
        )
    }
}
