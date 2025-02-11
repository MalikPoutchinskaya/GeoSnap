package com.kayakstudio.geosnap.ui.features.profile
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun ProfileNavigator() {
    Navigator(ProfileScreen) { navigator -> SlideTransition(navigator) }
}