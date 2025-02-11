package com.kayakstudio.geosnap.data.playerandpicture

import com.kayakstudio.geosnap.data.picture.toEntity
import com.kayakstudio.geosnap.data.picture.toModel
import com.kayakstudio.geosnap.data.player.toEntity
import com.kayakstudio.geosnap.data.player.toModel

fun PlayerAndPictureDto.toModel(): PlayerAndPictureModel =
    PlayerAndPictureModel(
        player = player.toEntity().toModel(),
        picture = picture.toEntity().toModel()
    )
