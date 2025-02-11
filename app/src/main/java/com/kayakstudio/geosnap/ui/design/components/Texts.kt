package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Texts {
    @Composable
    fun Title(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) = Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        color = color,
        style = MaterialTheme.typography.headlineLarge,
    )

    @Composable
    fun SubTitle(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) =
        Text(
            text = text,
            modifier = modifier,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3D348B)
        )

    @Composable
    fun Body(text: String, modifier: Modifier = Modifier, color: Color = Color(0xFF757575)) = Text(
        text = text,
        modifier = modifier,
        fontSize = 14.sp,
        color = color,
    )

    @Composable
    fun Caption(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color(0xFF757575),
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip,
    ) =
        Text(
            text = text,
            modifier = modifier,
            fontSize = 12.sp,
            color = color,
            maxLines = maxLines,
            overflow=overflow
        )

    @Composable
    fun IconWithText(imageVector: ImageVector, text: String, color: Color = Color.Unspecified) {
        Row {
            Icon(
                imageVector = imageVector, // Replace with your desired icon
                contentDescription = "", // For accessibility
                tint = MaterialTheme.colorScheme.primary // Optional: Use theme colors
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
            Body(text = text, color = color)
        }
    }
}