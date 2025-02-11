package com.kayakstudio.geosnap.ui.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.ui.features.router.RouterScreen
import com.kayakstudio.geosnap.ui.theme.GeoSnapTheme
import org.koin.compose.koinInject

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
) {
    val appRepository = koinInject<AppRepository>()
    val isDarkModeEnabled by appRepository.observeIsDarkModeEnabled()
        .collectAsState(initial = false)

    GeoSnapTheme(
        darkTheme = isDarkModeEnabled,
        dynamicColor = dynamicColor,
    ) {
        Navigator(RouterScreen)
    }
}