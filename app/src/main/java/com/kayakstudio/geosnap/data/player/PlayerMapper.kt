package com.kayakstudio.geosnap.data.player

fun PlayerDto.toEntity(): PlayerEntity =
    PlayerEntity(
        id = this.id,
        displayName = this.displayName,
        imageUrl = this.imageUrl,
        aboutMe = this.aboutMe ?: "",
        points = this.points,
    )


fun PlayerEntity.toModel() =
    PlayerModel(
        id = this.id,
        displayName = this.displayName,
        imageUrl = this.imageUrl,
        aboutMe = this.aboutMe,
        points = this.points,
    )