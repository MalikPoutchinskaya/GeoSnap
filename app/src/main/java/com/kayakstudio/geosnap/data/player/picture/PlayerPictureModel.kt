package com.kayakstudio.geosnap.data.player.picture

import kotlinx.datetime.LocalDateTime

data class PlayerPictureModel(
    val id: String,
    val playerId: String,
    val url: String,
    val location: LocationModel,
    val dataTime: LocalDateTime,
)

data class LocationModel(
    val lat: Double,
    val lng: Double,
)