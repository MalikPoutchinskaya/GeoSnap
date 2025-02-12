package com.kayakstudio.geosnap.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @ColumnInfo("display_name") val displayName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "about_me") val aboutMe: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo("points") val points: Int,

    )