package com.kayakstudio.geosnap.data.stubs.samples

import com.google.android.gms.maps.model.LatLng
import com.kayakstudio.geosnap.data.player.PlayerDto
import com.kayakstudio.geosnap.data.player.picture.LocationDto
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.user.UserDto
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDateTime

object SampleDto {
    val user = UserDto(
        id = "",
        displayName = "",
        email = "",
        firstName = "",
        lastName = "",
        phoneNumber = null,
        imageUrl = null,
        aboutMe = ""
    )

    val player = PlayerDto(
        id = "",
        displayName = "",
        aboutMe = "",
        imageUrl = null,
        points = 0,
    )

    val playerPicture = PlayerPictureDto(
        id = "",
        playerId = "",
        url = "",
        location = LocationDto(0.0, 0.0),
        dateTime = LocalDateTime.now()
    )
}
