package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.player.PlayerAndPictureDto
import com.kayakstudio.geosnap.data.player.PlayerApi
import com.kayakstudio.geosnap.data.player.PlayerDto
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase.playerPictures
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase.players
import kotlinx.coroutines.delay

class PlayerApiStub : PlayerApi {

    override suspend fun getGeoGuessPictures(userId: String): ApiResponse<List<PlayerAndPictureDto>> {
        delay(500) // fake latency
        val response = players
            .filter { it.id != userId }
            .mapNotNull { player ->
                playerPictures
                    .firstOrNull { it.playerId == player.id }
                    ?.let {
                        PlayerAndPictureDto(
                            player = player,
                            picture = it
                        )
                    }
            }
        return ApiResponse.Success(response)
    }

    override suspend fun getPlayersRanking(userId: String): ApiResponse<List<PlayerDto>> {
        delay(500)
        val response = ArrayList(players) // copy players
        response.sortBy { it.points }
        return ApiResponse.Success(response)
    }
}
