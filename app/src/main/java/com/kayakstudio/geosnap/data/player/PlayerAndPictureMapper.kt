package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.player.picture.toEntity
import com.kayakstudio.geosnap.data.player.picture.toModel

fun PlayerAndPictureDto.toModel(): PlayerAndPictureModel =
    PlayerAndPictureModel(
        player = player.toEntity().toModel(),
        picture = picture.toEntity().toModel()
    )
