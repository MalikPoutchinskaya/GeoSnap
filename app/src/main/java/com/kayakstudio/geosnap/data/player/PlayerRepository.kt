package com.kayakstudio.geosnap.data.player

import com.kayakstudio.geosnap.data.apiutils.map
import com.kayakstudio.geosnap.data.playerandpicture.PlayerAndPictureModel
import com.kayakstudio.geosnap.data.playerandpicture.toModel

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
