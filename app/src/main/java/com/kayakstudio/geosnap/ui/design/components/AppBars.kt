package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

object AppBars {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Top(
        scrollBehavior: TopAppBarScrollBehavior? = null,
        title: String,
        colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
        onBackPress: (() -> Unit)? = null
    ) {
        LargeTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Column(
                    modifier = Modifier.padding(end = 16.dp),
                ) {
                    Text(text = title, fontWeight = FontWeight.Bold)
                }
            },
            navigationIcon = {
                onBackPress?.let {
                    IconButton(onClick = it) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                }
            },
            colors = colors
        )
    }

    @Composable
    fun Bottom(
        modifier: Modifier=Modifier,
        firstButtonText: String,
        onFirstButtonClicked: () -> Unit,
        isFirstButtonEnabled: Boolean = true,
        firstButtonPainter: Painter? = null,
        secondButtonText: String? = null,
        secondButtonPainter: Painter? = null,
        onSecondButtonClicked: (() -> Unit)? = null,
        thirdLabelText: String? = null,
        onThirdButtonClicked: (() -> Unit)? = null,
        thirdTextButtonText: String? = null,
        isLoading: Boolean = false
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.horizontalScreenPadding,
                    vertical = Dimens.verticalScreenPadding
                )
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Buttons.Primary(
                text = firstButtonText,
                painter = firstButtonPainter,
                isEnabled = isFirstButtonEnabled && isLoading.not(),
                onClick = { onFirstButtonClicked() },
            )
            if (secondButtonText != null && onSecondButtonClicked != null) {
                Buttons.Secondary(
                    text = secondButtonText,
                    painter = secondButtonPainter,
                    isEnabled = isLoading.not(),
                    onClick = { onSecondButtonClicked() },
                )
            }
            if (thirdLabelText != null && onThirdButtonClicked != null && thirdTextButtonText != null) {
                TextButton(onClick = onThirdButtonClicked, enabled = isLoading.not()) {
                    Row {
                        Text(text = thirdLabelText)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = thirdTextButtonText,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
            }
        }
    }
}