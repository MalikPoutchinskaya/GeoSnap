package com.kayakstudio.geosnap.ui.features.login.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.tokens.Dimens

@Composable
fun OnboardingView(onNext: () -> Unit) {
    Scaffolds.SafePadding {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = Dimens.verticalScreenPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                    painter = painterResource(R.drawable.landing_image),
                    contentDescription = "",
                )
                Spacer(Modifier.height(32.dp))
                Texts.Title(
                    text = stringResource(R.string.onboardingScreen_main_title1),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Texts.Title(
                    text = stringResource(R.string.onboardingScreen_main_title2),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Texts.Title(
                    text = stringResource(R.string.onboardingScreen_main_title3),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Text(
                    text = stringResource(R.string.onboardingScreen_main_description),
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp),
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(onClick = { onNext() }) {
                    Icon(
                        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowForward),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}