package com.kayakstudio.geosnap.data.user

import co.touchlab.kermit.Logger
import com.kayakstudio.geosnap.data.apiutils.map
import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelper
import com.kayakstudio.geosnap.data.stubs.samples.SampleEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val appRepository: AppRepository,
    private val authHelper: FirebaseAuthHelper,
) {

    //
    // ACCOUNT
    // -----------------------------------------------------

    suspend fun login(email: String, password: String): Result<Unit> {
        return authHelper.login(email, password).map { token ->
            Logger.d("Saving new token")
            appRepository.setToken(token)
            userApi.getProfile().map { userDto ->
                userDao.upsert(userDto.toEntity())
            }
        }
    }

    suspend fun register(
        email: String,
        firstName: String,
        lastName: String,
        phoneNumber: String?,
    ): Result<Unit> {
        return authHelper.signup(
            email = email, password = "azerty", userName = "$firstName $lastName"
        ).map {}.onSuccess {
            authHelper.sendPasswordResetEmail(email)
        }
//        return userApi.register(
//            displayName = "$firstName $lastName",
//            email = email,
//            firstName = firstName,
//            lastName = lastName,
//            phoneNumber = phoneNumber
//        ).map {}.onSuccess {
//            authHelper.sendPasswordResetEmail(email)
//        }
    }

    //
    // GET
    // -----------------------------------------------------

    suspend fun getUserOrThrow(): UserModel = userDao.getUserOrThrow().toModel()

    //
    // Observe
    // -----------------------------------------------------

    fun observeUser(): Flow<UserModel?> =
        userDao.observeUser().map { it?.toModel() }

    //
    // Update
    // -----------------------------------------------------

    suspend fun updateProfileImgLocally(imageUrl: String) {
        val user = userDao.getUserOrThrow().copy(imageUrl = imageUrl)
        return userDao.upsert(user)
    }

    suspend fun updateAboutMeLocally(aboutMe: String) {
        val user = userDao.getUserOrThrow().copy(aboutMe = aboutMe)
        return userDao.upsert(user)
    }


}
