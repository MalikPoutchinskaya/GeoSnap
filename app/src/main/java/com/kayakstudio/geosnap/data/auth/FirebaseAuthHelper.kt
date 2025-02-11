package com.kayakstudio.geosnap.data.auth

interface FirebaseAuthHelper {
    suspend fun signup(email: String, password: String, userName: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<String>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun signInAnonymously(): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun getOrThrowUserId(): String
    suspend fun refreshToken(): String?
}