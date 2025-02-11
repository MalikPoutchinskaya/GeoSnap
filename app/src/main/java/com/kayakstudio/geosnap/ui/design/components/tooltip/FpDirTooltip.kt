package com.kayakstudio.geosnap.ui.design.components.tooltip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TooltipScope.FpDirTooltip(
    title: String,
    description: String,
    description2: String? = null,
    buttonLabel: String,
    onDismissTooltip: () -> Unit,
) {
    PlainTooltip(
        caretSize = DpSize(12.dp, 24.dp),
        shape = TooltipDefaults.richTooltipContainerShape,
        shadowElevation = 4.dp,
        tonalElevation = 4.dp,
    ) {
        Column(
            modifier =
            Modifier
                .padding(
                    vertical = Dimens.verticalScreenPadding,
                    horizontal = 8.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Outlined.EmojiObjects,
                        contentDescription = "",
                    )
                    Spacer(Modifier.height(8.dp))
                    Texts.Title(title)
                }
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
            description2?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onDismissTooltip) {
                    Text(buttonLabel)
                }
            }
        }
    }
}