package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.document.DocumentApi
import com.kayakstudio.geosnap.data.picture.LocationDto
import com.kayakstudio.geosnap.data.picture.PlayerPictureDto
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase
import com.kayakstudio.geosnap.data.stubs.scenario.USER_ID
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime

class DocumentApiStub : DocumentApi {

    override suspend fun postProfilePicture(userId: String, img: ByteArray): ApiResponse<String> {
        delay(500)
        return ApiResponse.Success("https://ipj.eu/wp-content/uploads/2023/06/denis-brogniart-scaled.jpeg")
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
