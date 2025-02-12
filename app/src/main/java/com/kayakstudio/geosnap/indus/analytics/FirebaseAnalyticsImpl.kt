package com.kayakstudio.geosnap.indus.analytics

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.Event
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.Param
import dev.gitlive.firebase.analytics.analytics

class FirebaseAnalyticsImpl(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {

    override fun trackEvent(event: AnalyticsEvent) {
        when (event) {
            // User Interaction Events
            is AnalyticsEvent.UserSignupInitiated -> logEvent("user_signup_initiated")
            is AnalyticsEvent.UserSignupCompleted -> logEvent("user_signup_completed")
            is AnalyticsEvent.UserLoginInitiated -> logEvent("user_login_initiated")
            is AnalyticsEvent.UserLoginSuccess -> logEvent("user_login_success")
            is AnalyticsEvent.UserLogout -> logEvent("user_logout")

            // Button Click Events
            AnalyticsEvent.GeoGuesserCardClicked -> logEvent("geo_guesser_card_clicked")

            // Feature Interaction Events
            AnalyticsEvent.GeoGuesserFinished -> logEvent("geo_guesser_finished")

            // Screen Navigation Events
            is AnalyticsEvent.ScreenView -> logEvent(
                eventName = Firebase.analytics.Event.SCREEN_VIEW,
                parameters = mapOf(Firebase.analytics.Param.SCREEN_NAME to event.screenName.name)
            )

            is AnalyticsEvent.SessionDuration -> logEvent("session_duration")
            is AnalyticsEvent.BounceRate -> logEvent("bounce_rate")

        }
    }

    // Helper function to log events
    private fun logEvent(eventName: String, parameters: Map<String, Any>? = null) {
        firebaseAnalytics.logEvent(eventName, parameters)
    }
}
