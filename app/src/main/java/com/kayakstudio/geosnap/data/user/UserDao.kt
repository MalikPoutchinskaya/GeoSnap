package com.kayakstudio.geosnap.data.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface UserDao {
    suspend fun upsert(newObject: UserEntity)

    fun observeUserOrThrow(): Flow<UserEntity>

    fun observeUser(): Flow<UserEntity?>

    suspend fun getUserOrThrow(): UserEntity

    suspend fun deleteAll()
}

@Dao
abstract class RoomUserDao : UserDao {
    @Upsert
    abstract override suspend fun upsert(newObject: UserEntity)

    @Query("SELECT * FROM users")
    abstract override fun observeUserOrThrow(): Flow<UserEntity>

    @Query("SELECT * FROM users")
    abstract override fun observeUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users")
    abstract override suspend fun getUserOrThrow(): UserEntity

    @Query("DELETE FROM users")
    abstract override suspend fun deleteAll()
}
