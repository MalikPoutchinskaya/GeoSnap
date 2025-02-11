package com.kayakstudio.geosnap.data.player.picture

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerPictureDto(
    @SerialName("id") val id: String,
    @SerialName("playerId") val playerId: String,
    @SerialName("url") val url: String,
    @SerialName("location") val location: LocationDto,
    @SerialName("dateTime") val dateTime: LocalDateTime,
)

@Serializable
data class LocationDto(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double,
)