package com.kayakstudio.geosnap.ui.features.geoguesser

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun GeoGuesserNavigator() {
    Navigator(GeoGuesserScreen) { navigator -> SlideTransition(navigator) }
}