package com.kayakstudio.geosnap.data.stubs.datasources

import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelper

class FirebaseAuthHelperStub: FirebaseAuthHelper {
    override suspend fun signup(email: String, password: String, userName: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return Result.success("")
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signInAnonymously(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signOut(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getOrThrowUserId(): String {
        return "firebase_user_id"
    }

    override suspend fun refreshToken(): String? {
        TODO("Not yet implemented")
    }
}