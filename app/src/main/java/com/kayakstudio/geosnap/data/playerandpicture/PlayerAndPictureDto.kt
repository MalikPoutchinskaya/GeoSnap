package com.kayakstudio.geosnap.data.playerandpicture

import com.kayakstudio.geosnap.data.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.player.PlayerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerAndPictureDto(
    @SerialName("player") val player: PlayerDto,
    @SerialName("picture") val picture: PlayerPictureDto,
)