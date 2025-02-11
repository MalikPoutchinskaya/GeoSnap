package com.kayakstudio.geosnap.indus.crashlytics

import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.data.user.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics

/**
 * Good Practices:
 *
 * Use Descriptive and Meaningful Names:
 * Custom keys should reflect the data they represent. Avoid generic names like key1, valueA.
 * Instead, use descriptive names like userRole, apiVersion, transactionID.
 *
 * Group Related Keys:
 * If several keys are related to a specific feature or flow, group them logically using a prefix
 * or suffix. Example: auth_userID, auth_tokenExpiration for authentication-related keys.
 */
class CrashlyticsSession(
    private val appRepository: AppRepository,
    private val userRepo: UserRepository,
) {

    suspend fun initialize() {
        refresh()
    }

    private suspend fun refresh() {
        appRepository.getToken()?.let {
            val user = userRepo.getUserOrThrow()

            val crashlytics = Firebase.crashlytics

            crashlytics.setUserId(user.id)

            val keys =
                mapOf<String, Any>(
                    "auth_userId" to user.id,
                    "auth_userName" to user.getFullName(),
                )
            crashlytics.setCustomKeys(keys)
        }
    }
}
