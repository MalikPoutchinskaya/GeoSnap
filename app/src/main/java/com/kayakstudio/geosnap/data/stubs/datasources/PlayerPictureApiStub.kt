package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.picture.PlayerPictureApi
import com.kayakstudio.geosnap.data.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase.playerPictures
import kotlinx.coroutines.delay

class PlayerPictureApiStub : PlayerPictureApi {
    override suspend fun getUserGeoGuessHistory(userId: String): ApiResponse<List<PlayerPictureDto>> {
        delay(500) // fake latency
        val response =
            playerPictures.filter { it.playerId == userId }.sortedByDescending { it.dateTime }
        return ApiResponse.Success(response)
    }
}
