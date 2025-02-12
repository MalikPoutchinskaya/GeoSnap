package com.kayakstudio.geosnap.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("displayName") val displayName: String,
    @SerialName("email") val email: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("phoneNumber") val phoneNumber: String? = null,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("aboutMe") val aboutMe: String? = null,
)
