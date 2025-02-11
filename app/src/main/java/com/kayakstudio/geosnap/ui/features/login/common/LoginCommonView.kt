package com.kayakstudio.geosnap.ui.features.login.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.BoxScaffold
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.components.snackbar.GeoSnapSnackbarHost
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

@Composable
fun SimpleCommonView(
    title: String,
    description: String,
    isLoading: Boolean,
    firstButtonText: String,
    secondButtonText: String? = null,
    secondButtonPainter: Painter? = null,
    thirdLabelText: String? = null,
    thirdTextButtonText: String? = null,
    onFirstButtonClicked: () -> Unit,
    onSecondButtonClicked: (() -> Unit)? = null,
    onThirdButtonClicked: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        snackbarHost = { GeoSnapSnackbarHost() },
        topBar = { TopBar(onNavigateBack) },
        bottomBar = {
            AppBars.Bottom(
                firstButtonText = firstButtonText,
                onFirstButtonClicked = onFirstButtonClicked,
                secondButtonText = secondButtonText,
                secondButtonPainter = secondButtonPainter,
                onSecondButtonClicked = onSecondButtonClicked,
                thirdLabelText = thirdLabelText,
                onThirdButtonClicked = onThirdButtonClicked,
                thirdTextButtonText = thirdTextButtonText,
                isLoading = isLoading
            )
        },
        content = { paddingValues ->
            ScreenContent(isLoading, paddingValues, title, description, content)
        }
    )
}

@Composable
private fun ScreenContent(
    isLoading: Boolean,
    paddingValues: PaddingValues,
    title: String,
    description: String,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    BoxScaffold(isLoading = isLoading, useScreenPadding = false, paddingValues = paddingValues) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = Dimens.horizontalScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(92.dp))
            Texts.Title(
                text = title,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = description,
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(64.dp))
            content()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onNavigateBack: (() -> Unit)?) {
    TopAppBar(
        title = {},
        navigationIcon = {
            if (onNavigateBack != null) {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "ArrowBack",
                    )
                }
            }
        },
    )

}