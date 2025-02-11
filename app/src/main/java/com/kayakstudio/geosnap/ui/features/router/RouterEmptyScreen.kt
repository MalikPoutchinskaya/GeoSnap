package com.kayakstudio.geosnap.ui.features.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kayakstudio.geosnap.ui.design.components.BoxScaffold

object RouterEmptyScreen : Screen {

    @Composable
    override fun Content() {
        BoxScaffold(
            paddingValues = PaddingValues(),
            isLoading = true,
            content = {}
        )
    }
}