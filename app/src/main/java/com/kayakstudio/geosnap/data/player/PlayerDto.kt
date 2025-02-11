package com.kayakstudio.geosnap.data.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("id") val id: String,
    @SerialName("userName") val displayName: String,
    @SerialName("imageUrl") val imageUrl: String? = null,
    @SerialName("points") val points: Int,
    @SerialName("aboutMe") val aboutMe: String? = null,
)