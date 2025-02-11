package com.kayakstudio.geosnap.data.player.picture

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface PlayerPictureDao {
    suspend fun upsert(newObjects: List<PlayerPictureEntity>)
    fun observePlayerPictures(playerId: String): Flow<List<PlayerPictureEntity>>

    suspend fun deleteAll()
}

@Dao
abstract class RoomPlayerPictureDao : PlayerPictureDao {
    @Upsert
    abstract override suspend fun upsert(newObjects: List<PlayerPictureEntity>)

    @Query("SELECT * FROM player_picture WHERE player_id = :playerId")
    abstract override fun observePlayerPictures(playerId: String): Flow<List<PlayerPictureEntity>>

    @Query("DELETE FROM Players")
    abstract override suspend fun deleteAll()
}
