package com.kayakstudio.geosnap.ui.features.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun DashboardNavigator() {
    Navigator(DashboardScreen) { navigator -> SlideTransition(navigator) }
}