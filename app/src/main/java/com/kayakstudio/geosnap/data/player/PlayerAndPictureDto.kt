package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.player.picture.PlayerPictureDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerAndPictureDto(
    @SerialName("player") val player: PlayerDto,
    @SerialName("picture") val picture: PlayerPictureDto,
)