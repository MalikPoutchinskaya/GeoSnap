package com.kayakstudio.geosnap.data.player

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface PlayerDao {
    suspend fun upsert(newObject: PlayerEntity)

    fun observePlayerOrThrow(): Flow<PlayerEntity>

    fun observePlayer(): Flow<PlayerEntity?>

    suspend fun getPlayerOrThrow(): PlayerEntity

    suspend fun deleteAll()
}

@Dao
abstract class RoomPlayerDao : PlayerDao {
    @Upsert
    abstract override suspend fun upsert(newObject: PlayerEntity)

    @Query("SELECT * FROM Players")
    abstract override fun observePlayerOrThrow(): Flow<PlayerEntity>

    @Query("SELECT * FROM Players")
    abstract override fun observePlayer(): Flow<PlayerEntity?>

    @Query("SELECT * FROM Players")
    abstract override suspend fun getPlayerOrThrow(): PlayerEntity

    @Query("DELETE FROM Players")
    abstract override suspend fun deleteAll()
}
