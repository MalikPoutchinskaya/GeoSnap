package com.kayakstudio.geosnap.data.player.picture

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.apiutils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface PlayerPictureApi {
    suspend fun getUserGeoGuessHistory(userId: String): ApiResponse<List<PlayerPictureDto>>
}

class KtorPlayerPictureApi(
    private val client: HttpClient,
) : PlayerPictureApi {
    override suspend fun getUserGeoGuessHistory(userId: String): ApiResponse<List<PlayerPictureDto>> {
        return safeRequest {
            client.get("/player/$userId/geoguesshistory")
        }
    }
}