package com.kayakstudio.geosnap.data.app

import com.kayakstudio.geosnap.data.GeoSnapDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AppRepository(
    private val keyValueStorage: KeyValueStorage,
    private val db: GeoSnapDataBase,
) {
    //
    // Observe
    // -----------------------------------------------------

    fun observerToken(): Flow<String?> {
        return keyValueStorage.observableToken
    }

    //
    // Get
    // -----------------------------------------------------

    fun getToken(): String? {
        return keyValueStorage.token
    }

    fun getIsDarkModeEnabled(): Boolean {
        return keyValueStorage.isDarkModeEnabled ?: false
    }

    fun getIsNotificationsEnabled(): Boolean {
        return keyValueStorage.isNotificationsEnabled ?: false
    }

    //
    // Get
    // -----------------------------------------------------

    fun observeIsDarkModeEnabled(): Flow<Boolean> {
        return keyValueStorage.observableIsDarkModeEnabled.map { it ?: false }
    }

    fun observeIsNotificationsEnabled(): Flow<Boolean> {
        return keyValueStorage.observableIsNotificationsEnabled.map { it ?: false }
    }

    //
    // Update
    // -----------------------------------------------------

    fun setToken(token: String) {
        keyValueStorage.token = token
    }

    fun resetToken() {
        keyValueStorage.token = null
    }

    fun setIsNotificationsEnabled(isNotificationsEnabled: Boolean) {
        keyValueStorage.isNotificationsEnabled = isNotificationsEnabled
    }

    fun setIsDarkModeEnabled(isDarkModeEnabled: Boolean) {
        keyValueStorage.isDarkModeEnabled = isDarkModeEnabled
    }

    //
    // Delete
    // -----------------------------------------------------

    fun cleanStorage() {
        keyValueStorage.cleanStorage()
    }

    suspend fun clearAllTables() {
        db.deleteAll()
    }
}
