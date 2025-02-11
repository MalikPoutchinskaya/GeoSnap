package com.kayakstudio.geosnap.ui.features.geoguesser

import com.google.android.gms.maps.model.LatLng

data class PlayerPictureResult(
    val playerId:String,
    val pictureId:String,
    val selectedLocation: LatLng,
    val distanceInMeters: Int
)