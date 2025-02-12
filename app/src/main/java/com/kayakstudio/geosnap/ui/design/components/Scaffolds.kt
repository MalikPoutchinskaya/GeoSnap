package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.snackbar.GeoSnapSnackbarHost
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

object Scaffolds {
    /**
     * used for root screen: it handle unique property like snackbarHost
     */
    @Composable
    fun Root(
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        containerColor: Color = MaterialTheme.colorScheme.background,
        contentColor: Color = contentColorFor(containerColor),
        contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = { GeoSnapSnackbarHost() },
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            content = { _ ->
                content()
            }
        )
    }

    /**
     *
     */
    @Composable
    fun SafePadding(
        useDefaultHorizontalScreenPadding: Boolean = true,
        isNestedTabScreen: Boolean = false,
        modifier: Modifier = Modifier,
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        containerColor: Color = MaterialTheme.colorScheme.background,
        contentColor: Color = contentColorFor(containerColor),
        contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
        content: @Composable () -> Unit,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            containerColor = containerColor,
            contentColor = contentColor,
            contentWindowInsets = contentWindowInsets,
            content = { paddingValues ->
                val horizontalPadding =
                    if (useDefaultHorizontalScreenPadding) Dimens.horizontalScreenPadding else 0.dp
                val boxModifier = if (isNestedTabScreen) {
                    Modifier.padding(top = paddingValues.calculateTopPadding())
                } else {
                    Modifier.padding(paddingValues)
                }
                Box(boxModifier.padding(horizontal = horizontalPadding)) {
                    content()
                }
            }
        )
    }
}