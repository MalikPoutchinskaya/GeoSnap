package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object BottomSheets {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Default(
        sheetState: SheetState,
        scope: CoroutineScope,
        title: String,
        content: String,
        positiveButtonLabel: String,
        positiveButtonPainter: Painter? = null,
        negativeButtonLabel: String? = null,
        negativeButtonPainter: Painter? = null,
        onDismissed: (() -> Unit)? = null,
        onPositiveClicked: () -> Unit,
        onNegativeClicked: (() -> Unit)? = null,
    ) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope
                    .launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissed?.invoke()
                        }
                    }
            },
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = Dimens.horizontalScreenPadding,
                    vertical = Dimens.verticalScreenPadding
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.Title(title)
                Spacer(modifier = Modifier.height(32.dp))
                Texts.Body(content)
                Spacer(modifier = Modifier.height(32.dp))
                AppBars.Bottom(
                    firstButtonText = positiveButtonLabel,
                    firstButtonPainter = positiveButtonPainter,
                    secondButtonText = negativeButtonLabel,
                    secondButtonPainter = negativeButtonPainter,
                    onFirstButtonClicked = {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onPositiveClicked()
                                }
                            }
                    },
                    onSecondButtonClicked = {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onNegativeClicked?.invoke()
                                }
                            }
                    }
                )
            }
        }
    }

}