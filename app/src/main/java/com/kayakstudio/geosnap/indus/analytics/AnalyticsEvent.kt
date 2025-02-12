package com.kayakstudio.geosnap.indus.analytics


sealed class AnalyticsEvent {

    // User Interaction Events
    data object UserSignupInitiated : AnalyticsEvent()
    data object UserSignupCompleted : AnalyticsEvent()
    data object UserLoginInitiated : AnalyticsEvent()
    data object UserLoginSuccess : AnalyticsEvent()
    data object UserLogout : AnalyticsEvent()

    // Button Click Events
    data object GeoGuesserCardClicked : AnalyticsEvent()

    // Feature Interaction Events
    data object GeoGuesserFinished : AnalyticsEvent()

    // Screen Navigation Events
    data class ScreenView(val screenName: ScreenName) : AnalyticsEvent()
    data object SessionDuration : AnalyticsEvent()
    data object BounceRate : AnalyticsEvent()

    enum class ScreenName {
        DASHBOARD, GEO_GUESSER
    }
}
