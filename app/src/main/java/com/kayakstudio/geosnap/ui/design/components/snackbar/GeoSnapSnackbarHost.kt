package com.kayakstudio.geosnap.ui.design.components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import com.kayakstudio.geosnap.ui.design.components.snackbar.SnackbarManager.DATA_SEPARATOR

@Composable
fun GeoSnapSnackbarHost(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(
            horizontal = Dimens.horizontalScreenPadding,
            vertical = Dimens.verticalScreenPadding
        )
    ) {
        SnackbarHost(
            hostState = SnackbarManager.snackbarHostState,
        ) { data ->
            val parts = data.visuals.message.split(DATA_SEPARATOR)
            val title = parts[0]
            val description = parts.getOrNull(1) ?: ""
            val type = SnackbarType.valueOf(parts.getOrNull(2) ?: SnackbarType.INFO.name)
            val actionLabel = parts.getOrNull(3)

            GeoSnapSnackbar(title, description, type, actionLabel){
                SnackbarManager.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}