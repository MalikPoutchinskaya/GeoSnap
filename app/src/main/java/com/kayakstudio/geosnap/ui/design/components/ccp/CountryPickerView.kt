package com.kayakstudio.geosnap.ui.design.components.ccp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.ccp.Country
import com.kayakstudio.geosnap.ui.design.components.ccp.CountryCodePickerDialog
import com.kayakstudio.geosnap.ui.design.components.ccp.getFlagEmojiFor

@Composable
fun CountryPickerView(
    selectedCountry: Country,
    countries: List<Country>,
    isEnabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    onSelection: (Country) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Text(
        modifier = Modifier
            .clickable {
                showDialog = true
            }
            .padding(start = 20.dp, end = 5.dp),
        text = "${getFlagEmojiFor(selectedCountry.nameCode)} +${selectedCountry.code}",
        color = textColor
    )

    if (showDialog)
        CountryCodePickerDialog(countries, onSelection) {
            showDialog = false
        }
}