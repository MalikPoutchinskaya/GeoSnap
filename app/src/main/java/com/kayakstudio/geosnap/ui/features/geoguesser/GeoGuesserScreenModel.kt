package com.kayakstudio.geosnap.ui.features.geoguesser

import cafe.adriel.voyager.core.model.screenModelScope
import com.google.android.gms.maps.model.LatLng
import com.kayakstudio.geosnap.data.player.PlayerRepository
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.tools.extensions.safeMessage
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class GeoGuesserScreenModel(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository,
) : MviScreenModel<GeoGuesserContract.Event, GeoGuesserContract.State, GeoGuesserContract.Effect>() {


    override fun createInitialState(): GeoGuesserContract.State {
        return GeoGuesserContract.State(
            playersAndPictures = listOf(),
            playerPictureResults = listOf(),
            index = 0,
            isLoading = true,
            isRevealed = false,
            isFinished = false
        )
    }

    override fun handleEvent(event: GeoGuesserContract.Event) {
        when (event) {
            GeoGuesserContract.Event.OnBackClicked ->
                setEffect { GeoGuesserContract.Effect.NavigateBack }

            is GeoGuesserContract.Event.OnReveal ->
                onReveal(
                    playerId = event.playerId,
                    pictureId = event.pictureId,
                    selectedLocation = event.selectedLocation
                )

            GeoGuesserContract.Event.OnNext -> {
                onNext()
            }
        }
    }


    init {
        screenModelScope.launch {
            val user = userRepository.getUserOrThrow()
            playerRepository.getGeoGuessPictures(user.id)
                .onSuccess {
                    setState { copy(playersAndPictures = it, isLoading = false) }
                }
                .onFailure {
                    setEffect { GeoGuesserContract.Effect.ShowErrorMessage(it.safeMessage()) }
                    setState { copy(isLoading = false) }
                }
        }
    }

    /**
     *
     */
    private fun onReveal(playerId: String, pictureId: String, selectedLocation: LatLng) {
        screenModelScope.launch {
            val result = PlayerPictureResult(
                playerId = playerId,
                pictureId = pictureId,
                selectedLocation = selectedLocation,
                distanceInMeters = 0
            )
            val results = currentState.playerPictureResults.toMutableList()
            results.add(result)
            setState { copy(isRevealed = true, playerPictureResults = results) }
        }
    }

    /**
     *
     */
    private fun onNext() {
        val newIndex = currentState.index + 1
        if (currentState.playersAndPictures.size > newIndex) {
            setState { copy(index = newIndex, isRevealed = false, isLoading = false) }
        } else {
            setState { copy(isFinished = true, isLoading = false) }
        }
    }
}
