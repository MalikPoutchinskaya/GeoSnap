package com.kayakstudio.geosnap.data.playerandpicture

import com.kayakstudio.geosnap.data.picture.PlayerPictureModel
import com.kayakstudio.geosnap.data.player.PlayerModel

data class PlayerAndPictureModel(
    val player: PlayerModel,
    val picture: PlayerPictureModel,
)