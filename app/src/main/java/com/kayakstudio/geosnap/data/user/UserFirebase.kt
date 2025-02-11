package com.kayakstudio.geosnap.data.user

data class UserFirebase(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val pictureUrl: String?,
)