package com.kayakstudio.geosnap.ui.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.ui.design.components.Pictures
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardContract

@Composable
fun RankingList(rankingStatus: DashboardContract.RankingStatus) {
    val rankWidth = 40.dp
    val ptsWidth = rankWidth

    Column(modifier = Modifier.fillMaxWidth()) {
        when (rankingStatus) {
            DashboardContract.RankingStatus.Error -> {
                Text("An error occurred")
            }

            DashboardContract.RankingStatus.Loading -> CircularProgressIndicator()
            is DashboardContract.RankingStatus.Success -> {
                rankingStatus.players
                    .sortedByDescending { it.points }
                    .forEachIndexed { index, player ->
                        val bgBrushedColors = if (index == 0) {
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer,
                            )
                        } else {
                            listOf(Color.Transparent, Color.Transparent)
                        }
                        val gradient = Brush.horizontalGradient(bgBrushedColors)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = gradient,
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Pictures.Avatar(imageUrl = player.imageUrl ?: "", height = 40.dp)
                            Column(modifier = Modifier.weight(1f)) {
                                Texts.Body(text = player.displayName)
                                Texts.Caption(
                                    modifier = Modifier,
                                    text = player.aboutMe,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Texts.Body(
                                modifier = Modifier
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                                    .wrapContentHeight(Alignment.CenterVertically),
                                text = player.points.toString(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
            }
        }
    }
}