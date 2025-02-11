package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.player.picture.PlayerPictureModel

data class PlayerAndPictureModel(
    val player: PlayerModel,
    val picture: PlayerPictureModel,
)