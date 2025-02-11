package com.kayakstudio.geosnap.data.picture

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Entity(tableName = "player_picture")
data class PlayerPictureEntity(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @ColumnInfo("player_id") val playerId: String,
    @ColumnInfo("url") val url: String,
    @Embedded val location: LocationEntity,
    @ColumnInfo("data_time") val dataTime: LocalDateTime
)

@Serializable
data class LocationEntity(
    @ColumnInfo("lat") val lat: Double,
    @ColumnInfo("lng") val lng: Double,
)