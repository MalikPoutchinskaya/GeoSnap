package com.kayakstudio.geosnap.ui.features.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.features.login.onboarding.OnboardingScreen
import com.kayakstudio.geosnap.ui.features.main.MainScreen
import kotlinx.coroutines.flow.collectLatest

object RouterScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel: RouterScreenModel = getScreenModel<RouterScreenModel>()

        Navigator(RouterEmptyScreen) { navigator ->
            // todo: fix transition for login screen
//            FadeTransition(navigator)
            LaunchedEffect(Unit) {
                screenModel.effect.collectLatest { effect ->
                    when (effect) {
                        RouterContract.Effect.NavigateToHomeScreen ->
                            navigator.replaceAll(MainScreen)

                        RouterContract.Effect.NavigateToLoginScreen ->
                            navigator.replaceAll(OnboardingScreen)
                    }
                }
            }
            
            Scaffolds.Root {
                CurrentScreen()
            }
        }
    }
}
