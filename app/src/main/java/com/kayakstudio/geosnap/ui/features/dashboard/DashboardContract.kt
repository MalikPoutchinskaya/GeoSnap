package com.kayakstudio.geosnap.ui.features.dashboard

import com.kayakstudio.geosnap.data.picture.PlayerPictureModel
import com.kayakstudio.geosnap.data.player.PlayerModel
import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState


class DashboardContract {

    sealed class Effect : UiEffect {
        data object NavigateToGeoGuessScreen : Effect()
        data class ShowErrorMessage(val message: String) : Effect()

    }

    sealed class Event : UiEvent {
        data object OnUserClickOnGeoGuessCard : Event()
    }

    data class State(
        val userName: String,
        val geoGuessHistoriesStatus: GeoGuessHistoriesStatus,
        val rankingStatus: RankingStatus,
    ) : UiState

    sealed class GeoGuessHistoriesStatus {
        data class Success(val geoGuessHistories: List<PlayerPictureModel>) :
            GeoGuessHistoriesStatus()

        data object Error : GeoGuessHistoriesStatus()
        data object Loading : GeoGuessHistoriesStatus()
    }

    sealed class RankingStatus {
        data class Success(val players: List<PlayerModel>) : RankingStatus()
        data object Error : RankingStatus()
        data object Loading : RankingStatus()
    }

}
