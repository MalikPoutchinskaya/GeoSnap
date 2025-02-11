package com.kayakstudio.geosnap.ui.features.geoguesser

import com.google.android.gms.maps.model.LatLng
import com.kayakstudio.geosnap.data.player.PlayerAndPictureModel
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureResult
import com.kayakstudio.geosnap.ui.tools.mvi.UiEffect
import com.kayakstudio.geosnap.ui.tools.mvi.UiEvent
import com.kayakstudio.geosnap.ui.tools.mvi.UiState

/**
 *
 */
class GeoGuesserContract {
    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object NavigateBack : Effect()
    }

    sealed class Event : UiEvent {
        data object OnBackClicked : Event()
        data class OnReveal(
            val playerId: String,
            val pictureId: String,
            val selectedLocation: LatLng
        ) : Event()

        data object OnNext : Event()
    }

    data class State(
        val isLoading: Boolean,
        val isRevealed: Boolean,
        val isFinished: Boolean,
        val playersAndPictures: List<PlayerAndPictureModel>,
        val playerPictureResults: List<PlayerPictureResult>,
        val index: Int,
    ) : UiState
}