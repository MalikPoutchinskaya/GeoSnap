package com.kayakstudio.geosnap.data.user

data class UserModel(
    val id: String,
    val displayName: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val aboutMe: String,
    val imageUrl: String?,
    val imageId: Int?,
) {
    fun getFullName() = "$firstName $lastName"
}