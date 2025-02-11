package com.kayakstudio.geosnap.data.picture

import com.kayakstudio.geosnap.data.apiutils.map
import com.kayakstudio.geosnap.data.player.PlayerApi
import com.kayakstudio.geosnap.data.player.PlayerDao
import com.kayakstudio.geosnap.data.picture.PlayerPictureModel
import com.kayakstudio.geosnap.data.picture.toEntity
import com.kayakstudio.geosnap.data.picture.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerPictureRepository(
    private val playerPictureApi: PlayerPictureApi,
    private val playerPictureDao: PlayerPictureDao,
) {


    //
    // GET
    // -----------------------------------------------------

    suspend fun getUserGeoGuessHistory(userId: String): Result<List<PlayerPictureModel>> {
        return playerPictureApi.getUserGeoGuessHistory(userId)
            .map { response ->
                val entities = response.map { it.toEntity() }
                playerPictureDao.upsert(entities)
                entities.map { it.toModel() }
            }
    }

    //
    // Observe
    // -----------------------------------------------------

    fun observeUserGeoGuessHistory(userId: String): Flow<List<PlayerPictureModel>> {
        return playerPictureDao.observePlayerPictures(userId)
            .map { list -> list.map { it.toModel() } }
    }

    //
    // Update
    // -----------------------------------------------------

}
