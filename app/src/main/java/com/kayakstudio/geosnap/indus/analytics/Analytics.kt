package com.kayakstudio.geosnap.indus.analytics

interface Analytics {
    fun trackEvent(event: AnalyticsEvent)
}