package com.kayakstudio.geosnap.ui.features.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kayakstudio.geosnap.R
import com.kayakstudio.geosnap.ui.design.components.AppBars
import com.kayakstudio.geosnap.ui.design.components.Scaffolds
import com.kayakstudio.geosnap.ui.design.components.Texts
import com.kayakstudio.geosnap.ui.design.tokens.Dimens
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardContract.GeoGuessHistoriesStatus
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardContract.RankingStatus
import com.kayakstudio.geosnap.ui.features.dashboard.components.GeoGuessCard
import com.kayakstudio.geosnap.ui.features.dashboard.components.GeoGuesserHistories
import com.kayakstudio.geosnap.ui.features.dashboard.components.RankingList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScaffold(
    uiState: DashboardContract.State,
    onEvent: (DashboardContract.Event) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffolds.SafePadding(
        isNestedTabScreen = true,
        useDefaultHorizontalScreenPadding = false,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBars.Top(
                scrollBehavior = scrollBehavior,
                title = stringResource(R.string.dashboardScreen_greeting_text, uiState.userName)
            )
        }
    ) {
        DashboardContent(uiState.rankingStatus, uiState.geoGuessHistoriesStatus) {
            onEvent(DashboardContract.Event.OnUserClickOnGeoGuessCard)
        }
    }
}

@Composable
fun DashboardContent(
    rankingStatus: RankingStatus,
    geoGuessHistoriesStatus: GeoGuessHistoriesStatus,
    onGeoGuessCardClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(vertical = 32.dp),
    ) {
        item {
            AnimatedVisibility(
                visible = true,
                Modifier.padding(horizontal = Dimens.horizontalScreenPadding)
            ) {
                GeoGuessCard {
                    onGeoGuessCardClicked()
                }
            }
        }
        item {
            Column {
                Texts.SubTitle(
                    modifier = Modifier.padding(horizontal = Dimens.horizontalScreenPadding),
                    text = stringResource(R.string.dashboardScreen_geoguess_text),
                )
                Spacer(modifier = Modifier.height(32.dp))
                GeoGuesserHistories(geoGuessHistoriesStatus) {}
            }
        }

        item {
            Column(Modifier.padding(horizontal = Dimens.horizontalScreenPadding)) {
                Texts.SubTitle(
                    text = stringResource(R.string.dashboardScreen_ranking_title),
                )
                Spacer(modifier = Modifier.height(32.dp))
                RankingList(rankingStatus)
            }
        }
    }
}
