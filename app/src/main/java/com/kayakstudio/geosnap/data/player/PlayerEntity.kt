package com.kayakstudio.geosnap.data.player

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @ColumnInfo("display_name") val displayName: String,
    @ColumnInfo(name = "about_me") val aboutMe: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "points") val points: Int,
)