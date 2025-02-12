package com.kayakstudio.geosnap.data.user

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.apiutils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface UserApi {
    suspend fun getProfile(): ApiResponse<UserDto>
}

class KtorUserApi(
    private val client: HttpClient,
) : UserApi {
    override suspend fun getProfile(): ApiResponse<UserDto> =
        safeRequest {
            client.get("/account")
        }
}
