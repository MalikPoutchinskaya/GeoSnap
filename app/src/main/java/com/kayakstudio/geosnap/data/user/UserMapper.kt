package com.kayakstudio.geosnap.data.user

fun UserDto.toEntity(): UserEntity =
    UserEntity(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber ?: "",
        imageUrl = this.imageUrl,
        aboutMe = this.aboutMe ?: "",
    )


fun UserEntity.toModel() =
    UserModel(
        id = this.id,
        displayName = this.displayName,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        imageUrl = this.imageUrl,
        aboutMe = this.aboutMe,
    )