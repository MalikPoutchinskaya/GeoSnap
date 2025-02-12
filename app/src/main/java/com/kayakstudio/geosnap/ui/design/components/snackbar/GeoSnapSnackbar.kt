package com.kayakstudio.geosnap.ui.design.components.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.tokens.Colors

@Composable
fun GeoSnapSnackbar(
    title: String,
    description: String,
    type: SnackbarType,
    actionLabel: String? = null,
    onActionClicked: (() -> Unit)? = null,
) {
    val (backgroundColor, textColor, flagColor) = when (type) {
        SnackbarType.ERROR -> Triple(
            MaterialTheme.colorScheme.surfaceContainerLowest,
            Colors.errorText,
            Colors.errorText,
        )

        SnackbarType.SUCCESS -> Triple(
            MaterialTheme.colorScheme.surfaceContainerLowest,
            Colors.successText,
            Colors.successText,
        )

        SnackbarType.INFO -> Triple(
            MaterialTheme.colorScheme.surfaceContainerLowest,
            Colors.infoText,
            Colors.infoText,
        )

        SnackbarType.WARN -> Triple(
            MaterialTheme.colorScheme.surfaceContainerLowest,
            Colors.warningText,
            Colors.warningText,
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
        ) {

            VerticalDivider(
                color = flagColor,
                thickness = 8.dp,
                modifier = Modifier
                    .padding(all = 0.dp)
                    .fillMaxHeight()
            )

            Column(modifier = Modifier.padding(all = 12.dp).fillMaxWidth(0.8f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val (icon, tint) = when (type) {
                        SnackbarType.ERROR -> Pair(
                            Icons.Default.Error,
                            Colors.errorText
                        )

                        SnackbarType.SUCCESS -> Pair(
                            Icons.Default.Check,
                            Colors.successText
                        )

                        SnackbarType.INFO -> Pair(
                            Icons.Default.Info,
                            Colors.infoText
                        )

                        SnackbarType.WARN -> Pair(
                            Icons.Default.Warning,
                            Colors.warningText
                        )
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = "Status Icon",
                        tint = tint
                    )

                    Spacer(modifier = Modifier.padding(all = 4.dp))
                    Text(text = title, color = textColor)
                }
                Spacer(modifier = Modifier.padding(all = 4.dp))
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (actionLabel != null && onActionClicked != null) {
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onActionClicked, modifier = Modifier.align(Alignment.Bottom)) {
                    Text(actionLabel, color = textColor)
                }
            }
        }
    }
}