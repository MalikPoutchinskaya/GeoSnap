package com.kayakstudio.geosnap.ui.features.dashboard

import cafe.adriel.voyager.core.model.screenModelScope
import com.kayakstudio.geosnap.data.player.PlayerRepository
import com.kayakstudio.geosnap.data.picture.PlayerPictureRepository
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.tools.extensions.safeMessage
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardContract.GeoGuessHistoriesStatus
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DashboardScreenModel(
    userRepository: UserRepository,
    playerPictureRepository: PlayerPictureRepository,
    playerRepository: PlayerRepository,
) :
    MviScreenModel<DashboardContract.Event, DashboardContract.State, DashboardContract.Effect>() {
    override fun createInitialState(): DashboardContract.State =
        DashboardContract.State(
            userName = "",
            geoGuessHistoriesStatus = GeoGuessHistoriesStatus.Loading,
            rankingStatus = DashboardContract.RankingStatus.Loading
        )

    override fun handleEvent(event: DashboardContract.Event) {
        when (event) {
            DashboardContract.Event.OnUserClickOnGeoGuessCard ->
                setEffect { DashboardContract.Effect.NavigateToGeoGuessScreen }
        }
    }

    /**
     *
     */
    private val exceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            val errorMessage = exception.safeMessage()
            setEffect { DashboardContract.Effect.ShowErrorMessage(errorMessage) }
        }

    init {
        screenModelScope.launch(exceptionHandler) {
            val user = userRepository.getUserOrThrow()
            setState { copy(userName = user.displayName) }
        }

        screenModelScope.launch(exceptionHandler) {
            val user = userRepository.getUserOrThrow()
            playerPictureRepository.getUserGeoGuessHistory(user.id)
                .onFailure {
                    setState { copy(geoGuessHistoriesStatus = GeoGuessHistoriesStatus.Error) }
                }
        }

        screenModelScope.launch(exceptionHandler) {
            val user = userRepository.getUserOrThrow()
            playerPictureRepository.observeUserGeoGuessHistory(user.id).collect {
                setState { copy(geoGuessHistoriesStatus = GeoGuessHistoriesStatus.Success(it)) }
            }
        }

        screenModelScope.launch(exceptionHandler) {
            val user = userRepository.getUserOrThrow()
            playerRepository.getPlayersRanking(user.id)
                .onSuccess {
                    setState { copy(rankingStatus = DashboardContract.RankingStatus.Success(it)) }
                }
                .onFailure {
                    setState { copy(rankingStatus = DashboardContract.RankingStatus.Error) }
                }
        }
    }
}