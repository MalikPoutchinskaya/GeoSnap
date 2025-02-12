package com.kayakstudio.geosnap.data.stubs.databases

import com.kayakstudio.geosnap.data.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.player.PlayerDto
import com.kayakstudio.geosnap.data.user.UserDto

object StubRemoteDatabase {
    val users = arrayListOf<UserDto>()
    val players = arrayListOf<PlayerDto>()
    val playerPictures = arrayListOf<PlayerPictureDto>()

    fun clear() {
        users.clear()
        players.clear()
        playerPictures.clear()
    }
}
