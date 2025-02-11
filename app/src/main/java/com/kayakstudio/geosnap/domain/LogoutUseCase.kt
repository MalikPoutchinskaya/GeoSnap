package com.kayakstudio.geosnap.domain

import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.indus.analytics.Analytics
import com.kayakstudio.geosnap.indus.analytics.AnalyticsEvent

class LogoutUseCase(
    private val appRepository: AppRepository,
    private val analytics: Analytics,
) {
    suspend operator fun invoke() {
        analytics.trackEvent(AnalyticsEvent.UserLogout)
        appRepository.clearAllTables()
        appRepository.cleanStorage()
    }
}