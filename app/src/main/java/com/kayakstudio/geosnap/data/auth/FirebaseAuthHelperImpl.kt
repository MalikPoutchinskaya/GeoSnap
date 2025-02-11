package com.kayakstudio.geosnap.data.auth

import com.kayakstudio.geosnap.indus.EnvHelper.isMockWithServer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth

class FirebaseAuthHelperImpl : FirebaseAuthHelper {
    private val auth: FirebaseAuth = Firebase.auth

    init {
        if (isMockWithServer) {
            auth.useEmulator("10.0.2.2", 9099)
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        userName: String,
    ): Result<Unit> =
        try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val firebaseUser =
                result.user ?: throw IllegalStateException("Firebase User should not be null")
            firebaseUser.updateProfile(userName)
            auth.updateCurrentUser(firebaseUser)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<String> =
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password)
            val firebaseUser =
                authResult.user ?: throw IllegalStateException("Firebase User should not be null")
            val token = firebaseUser.getIdToken(true)
            if (token != null) {
                Result.success(token)
            } else {
                Result.failure(IllegalArgumentException("The firebase token is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        try {
            auth.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun signInAnonymously(): Result<Unit> =
        try {
            auth.signInAnonymously()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun signOut(): Result<Unit> =
        try {
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getOrThrowUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalArgumentException("Firebase user UID should not be null")
    }

    override suspend fun refreshToken(): String? {
        val user = auth.currentUser
        val token = user?.getIdToken(true)
        return token
    }
}
