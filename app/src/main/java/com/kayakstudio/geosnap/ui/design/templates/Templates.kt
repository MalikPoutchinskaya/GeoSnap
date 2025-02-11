package com.kayakstudio.geosnap.ui.design.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

object Templates {
    @Composable
    fun Empty(
        title: String,
        description: String,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.horizontalScreenPadding),
            ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(92.dp))
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Default.ImageSearch,
                    contentDescription = "ImageSearch"
                )
                Spacer(modifier = Modifier.height(24.dp))
                Texts.Title(
                    text = title,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(64.dp))
//            Buttons.Primary()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }


    @Composable
    fun Error(
        title: String,
        description: String,
    ) {
        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = Dimens.horizontalScreenPadding),
            ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(92.dp))
                Icon(imageVector = Icons.Default.Error, contentDescription = "Error")
                Texts.Title(
                    text = title,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = description,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(64.dp))
//            Buttons.Primary()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @Composable
    fun Loading(
        title: String? = null,
        description: String? = null,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
