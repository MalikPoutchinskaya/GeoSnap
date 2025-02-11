package com.kayakstudio.geosnap.ui.features.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureModel
import com.kayakstudio.geosnap.tools.extensions.toReadableDayMonth
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardContract
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun InstagramStories(
    storiesStatus: DashboardContract.GeoGuessHistoriesStatus,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    LazyRow(
        modifier =
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = Dimens.horizontalScreenPadding),
    ) {
        when (storiesStatus) {
            DashboardContract.GeoGuessHistoriesStatus.Error -> {
                item { Text("An error occurred") }
            }

            DashboardContract.GeoGuessHistoriesStatus.Loading -> item { CircularProgressIndicator() }
            is DashboardContract.GeoGuessHistoriesStatus.Success -> {
                items(storiesStatus.geoGuessHistories) { geoGuessHistory ->
                    StoryItem(story = geoGuessHistory) { onClick() }
                }
            }
        }

    }
}

@Composable
fun StoryItem(
    story: PlayerPictureModel,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val gradient =
            Brush.horizontalGradient(
                listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.tertiaryContainer,
                ),
                startX = 0.0f,
                endX = 300.0f,
                tileMode = TileMode.Repeated,
            )

        KamelImage(
            resource = asyncPainterResource(data = story.url),
            contentDescription = "",
            modifier =
            Modifier
                .size(70.dp)
                .border(width = 3.dp, brush = gradient, shape = CircleShape)
                .padding(3.dp)
                .border(2.dp, Color.White, CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.Gray,
                    strokeWidth = 2.dp,
                )
            },
            onFailure = {
                Image(
                    imageVector = Icons.Default.NoPhotography,
                    contentDescription = "",
                )
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = story.dataTime.toReadableDayMonth(),
            fontSize = 12.sp,
            maxLines = 2,
        )
    }
}
