package com.kayakstudio.geosnap.data.player.picture

import com.google.android.gms.maps.model.LatLng

data class PlayerPictureResult(
    val playerId:String,
    val pictureId:String,
    val selectedLocation: LatLng,
    val distanceInMeters: Int
)