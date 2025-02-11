package com.kayakstudio.geosnap.data.picture

import com.google.android.gms.maps.model.LatLng

fun PlayerPictureDto.toEntity(): PlayerPictureEntity =
    PlayerPictureEntity(
        id = id,
        playerId = playerId,
        url = url,
        location = LocationEntity(location.lat, location.lng),
        dataTime = this.dateTime
    )


fun PlayerPictureEntity.toModel() =
    PlayerPictureModel(
        id = id,
        playerId = playerId,
        url = url,
        location = LocationModel(location.lat, location.lng),
        dataTime = dataTime
    )