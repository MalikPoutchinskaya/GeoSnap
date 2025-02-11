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
    fun CustomCollapsibleTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
        val isCollapsed = scrollBehavior.state.collapsedFraction > 0.5f

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isCollapsed) 80.dp else 200.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /* Gérer le retour */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /* Gérer la recherche */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Recherche",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Zone de titre et sous-titre, ajustée en fonction de la taille de l'AppBar
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Préparateur de commande",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "De La Croix • 16ieme, Paris • 1 day ago",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ajouter des composants supplémentaires
                Button(
                    onClick = { /* Action pour le bouton */ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Apply Now", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchAppBar(
        title: String,
        searchQuery: String,
        scrollBehavior: TopAppBarScrollBehavior,
        onSearchQueryChange: (String) -> Unit,
        onBackPress: () -> Unit
    ) {
        var isSearching by remember { mutableStateOf(true) }
        val isCollapsed = scrollBehavior.state.collapsedFraction > 0.5f

        LargeTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Column {
                    AnimatedVisibility(
                        visible = isSearching && isCollapsed.not(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(text = title)
                    }
                    AnimatedVisibility(
                        visible = isSearching,
                        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                        exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                    ) {
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = onSearchQueryChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            navigationIcon = {
                if (isSearching && isCollapsed) {
                    IconButton(
                        onClick = {
                            isSearching = false
                            onSearchQueryChange("") // Clear search query when exiting search mode
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                } else {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            },
            actions = {
                if (!isSearching) {
                    IconButton(onClick = { isSearching = true }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                        )
                    }
                }
            },
        )
    }

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