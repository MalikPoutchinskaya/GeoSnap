package com.kayakstudio.geosnap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kayakstudio.geosnap.data.player.PlayerEntity
import com.kayakstudio.geosnap.data.player.RoomPlayerDao
import com.kayakstudio.geosnap.data.player.picture.PlayerPictureEntity
import com.kayakstudio.geosnap.data.player.picture.RoomPlayerPictureDao
import com.kayakstudio.geosnap.data.user.RoomUserDao
import com.kayakstudio.geosnap.data.user.UserEntity

const val DATABASE_NAME = "geosnap.db"

@Database(
    entities = [UserEntity::class, PlayerEntity::class, PlayerPictureEntity::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class GeoSnapDataBase : RoomDatabase() {
    abstract fun userDao(): RoomUserDao
    abstract fun playerDao(): RoomPlayerDao
    abstract fun playerPictureDao(): RoomPlayerPictureDao

    suspend fun deleteAll() {
        userDao().deleteAll()
        playerDao().deleteAll()
        playerPictureDao().deleteAll()
    }
}
