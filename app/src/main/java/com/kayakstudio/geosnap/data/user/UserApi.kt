package com.kayakstudio.geosnap.data.user

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.apiutils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface UserApi {
    suspend fun getProfile(): ApiResponse<UserDto>
    suspend fun register(
        displayName: String,
        email: String,
        firstName: String,
        lastName: String,
        phoneNumber: String?,
    ): ApiResponse<UserDto>
}

class KtorUserApi(
    private val client: HttpClient,
) : UserApi {
    override suspend fun getProfile(): ApiResponse<UserDto> =
        safeRequest {
            client.get("/account")
        }

    override suspend fun register(
        displayName: String,
        email: String,
        firstName: String,
        lastName: String,
        phoneNumber: String?,
    ): ApiResponse<UserDto> =
        safeRequest {
            client.get("/register/candidate")
        }
}

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("displayName") val displayName: String,
    @SerialName("email") val email: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("phoneNumber") val phoneNumber: String? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("aboutMe") val aboutMe: String?=null,
)
