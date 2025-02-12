package com.kayakstudio.geosnap.indus.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import co.touchlab.kermit.Logger

@Composable
fun TrackScreen(analytics: Analytics, screenName: AnalyticsEvent.ScreenName) {
    DisposableEffect(Unit) {
        Logger.withTag("SCREEN_TRACKING").d("screen enter : $screenName")
        analytics.trackEvent(AnalyticsEvent.ScreenView(screenName))
        onDispose {
            Logger.withTag("SCREEN_TRACKING").d("screen exit : $screenName")
        }
    }
}