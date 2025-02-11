package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.apiutils.safeRequest
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface PlayerApi {
    suspend fun getGeoGuessPictures(userId: String): ApiResponse<List<PlayerAndPictureDto>>
    suspend fun getPlayersRanking(userId: String): ApiResponse<List<PlayerDto>>
}

class KtorPlayerApi(
    private val client: HttpClient,
) : PlayerApi {

    override suspend fun getGeoGuessPictures(userId: String): ApiResponse<List<PlayerAndPictureDto>> {
        return safeRequest {
            client.get("/player/$userId/geoguesspictures")
        }
    }

    override suspend fun getPlayersRanking(userId: String): ApiResponse<List<PlayerDto>> {
        return safeRequest {
            client.get("/player/$userId/ranking")
        }
    }
}