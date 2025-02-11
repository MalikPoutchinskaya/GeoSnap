package com.kayakstudio.geosnap.ui.design.components.snackbar

data class SnackbarDataHolder(
    val title: String,
    val description: String,
    val type: SnackbarType,
    val actionLabel: String? = null,
)