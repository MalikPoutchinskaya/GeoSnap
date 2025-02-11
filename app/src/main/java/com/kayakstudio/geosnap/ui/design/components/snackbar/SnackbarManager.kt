package com.kayakstudio.geosnap.ui.design.components.snackbar

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SnackbarManager {
    const val DATA_SEPARATOR = "&&"
    val snackbarHostState = SnackbarHostState()
    private var isSnackbarVisible = false

    fun CoroutineScope.showSuccessSnackbar(
        title: String,
        description: String,
        actionLabel: String? = null,
    ) {
        val message = SnackbarDataHolder(title, description, SnackbarType.SUCCESS, actionLabel)
        showSnackbar(message)
    }

    fun CoroutineScope.showErrorSnackbar(
        title: String,
        description: String,
        actionLabel: String? = null,
    ) {
        val message = SnackbarDataHolder(title, description, SnackbarType.ERROR, actionLabel)
        showSnackbar(message)
    }

    fun CoroutineScope.showInfoSnackbar(
        title: String,
        description: String,
        actionLabel: String? = null,
    ) {
        val message = SnackbarDataHolder(title, description, SnackbarType.INFO, actionLabel)
        showSnackbar(message)
    }

    fun CoroutineScope.showWarningSnackbar(
        title: String,
        description: String,
        actionLabel: String? = null,
    ) {
        val message = SnackbarDataHolder(title, description, SnackbarType.WARN, actionLabel)
        showSnackbar(message)
    }


    private fun CoroutineScope.showSnackbar(snackbarDataHolder: SnackbarDataHolder) {
//        if (!isSnackbarVisible) {
        launch {
//                isSnackbarVisible = true
            snackbarHostState.showSnackbar(
                message = with(snackbarDataHolder) {
                    listOf(
                        title,
                        description,
                        "$type",
                        actionLabel
                    ).filterNotNull().joinToString(separator = DATA_SEPARATOR)
                },
                actionLabel = snackbarDataHolder.actionLabel //use this to manage the infinite duration
            )
//                isSnackbarVisible = false
//            }
        }
    }
}