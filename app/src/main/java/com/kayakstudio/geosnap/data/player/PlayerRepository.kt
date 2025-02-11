package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.apiutils.map
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureModel
import com.kayakstudio.geosnap.data.player.picture.toEntity
import com.kayakstudio.geosnap.data.player.picture.toModel
import kotlinx.coroutines.flow.Flow

class PlayerRepository(
    private val playerApi: PlayerApi,
    private val playerDao: PlayerDao,
) {


    //
    // GET
    // -----------------------------------------------------

    suspend fun getGeoGuessPictures(userId: String): Result<List<PlayerAndPictureModel>> {
        return playerApi.getGeoGuessPictures(userId)
            .map { response -> response.map { it.toModel() } }
    }

    suspend fun getPlayersRanking(userId: String): Result<List<PlayerModel>> {
        return playerApi.getPlayersRanking(userId)
            .map { response -> response.map { it.toEntity().toModel() } }
    }

    //
    // Observe
    // -----------------------------------------------------


    //
    // Update
    // -----------------------------------------------------

}
