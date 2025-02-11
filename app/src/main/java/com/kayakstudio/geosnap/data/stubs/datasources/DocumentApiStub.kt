package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.document.DocumentApi
import com.kayakstudio.geosnap.data.player.picture.LocationDto
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase
import com.kayakstudio.geosnap.data.stubs.scenario.USER_ID
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime

class DocumentApiStub : DocumentApi {

    override suspend fun postProfilePicture(userId: String, img: ByteArray): ApiResponse<String> {
        delay(500)
        return ApiResponse.Success("https://media.licdn.com/dms/image/v2/C4D03AQFyKmuTjCpgZw/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1613555383548?e=1735776000&v=beta&t=VmVKgqJJbHS4Mwt14-0yTibeFGTrv431U1YWtwN0x3Y")
    }

    override suspend fun postGeoGuesserPicture(
        userId: String,
        img: ByteArray
    ): ApiResponse<String> {
        val url = "https://pbs.twimg.com/media/EMyyrk_WwAIvNuV.jpg:large"
        //FIXME
        StubRemoteDatabase.playerPictures.add(
            PlayerPictureDto(
                id = "denis100",
                playerId = USER_ID,
                url = url,
                location = LocationDto(19.6863094, -98.8950752),
                dateTime = LocalDateTime.now()
            )
        )
        delay(500)
        return ApiResponse.Success(url)
    }
}
