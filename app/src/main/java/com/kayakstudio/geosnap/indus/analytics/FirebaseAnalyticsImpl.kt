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
            is AnalyticsEvent.UniverseButtonClicked -> logEvent("universe_button_clicked")
            is AnalyticsEvent.PlaceAnOrderButtonClicked -> logEvent("place_an_order_button_clicked")
            is AnalyticsEvent.LogoutButtonClicked -> logEvent("logout_button_clicked")
            is AnalyticsEvent.DeleteAccountButtonClicked -> logEvent("delete_account_button_clicked")
            is AnalyticsEvent.ManualEditQuantityButtonClicked -> logEvent("manual_edit_quantity_button_clicked")
            is AnalyticsEvent.LongPressQuantityButtonClicked -> logEvent("long_press_quantity_button_clicked")

            // Feature Interaction Events
            is AnalyticsEvent.LoyaltyPointsEarned -> logEvent(
                eventName = "loyalty_points_earned",
                parameters = mapOf("rule_type" to event.ruleType.name)
            )

            is AnalyticsEvent.LoyaltyPointsRedeemed -> logEvent("loyalty_points_redeemed")
            is AnalyticsEvent.ProductViewed -> logEvent(
                eventName = "product_viewed",
                parameters = mapOf("product_id" to event.productId)
            )

            is AnalyticsEvent.ChildAccountAdded -> logEvent("child_account_added")
            AnalyticsEvent.ChildAccountDeleted -> logEvent("child_account_deleted")


            // Screen Navigation Events
            is AnalyticsEvent.ScreenView -> logEvent(
                eventName = Firebase.analytics.Event.SCREEN_VIEW,
                parameters = mapOf(Firebase.analytics.Param.SCREEN_NAME to event.screenName.name)
            )

            is AnalyticsEvent.SessionDuration -> logEvent("session_duration")
            is AnalyticsEvent.BounceRate -> logEvent("bounce_rate")

            // Checkout & Payment Events
            is AnalyticsEvent.CheckoutInitiated -> logEvent("checkout_initiated")
            is AnalyticsEvent.PaymentMethodSelected -> logEvent(
                eventName = "payment_method_selected",
                parameters = mapOf("payment_type" to event.paymentType.name)
            )

            is AnalyticsEvent.DiscountApplied -> logEvent("discount_applied")
            is AnalyticsEvent.CheckoutCompleted -> logEvent("checkout_completed")
            is AnalyticsEvent.CheckoutAbandoned -> logEvent("checkout_abandoned")

            // Loyalty Program Events
            is AnalyticsEvent.LoyaltyPointsEarnedForOrder -> logEvent(
                eventName = "loyalty_points_earned_for_order",
                parameters = mapOf("order_id" to event.orderId, "points" to event.points)
            )

            is AnalyticsEvent.LoyaltyPointsBalanceViewed -> logEvent("loyalty_points_balance_viewed")
            is AnalyticsEvent.LoyaltyEngagement -> logEvent(
                eventName = "loyalty_engagement",
                parameters = mapOf("engagement_type" to event.engagementType.name)
            )
        }
    }

    // Helper function to log events
    private fun logEvent(eventName: String, parameters: Map<String, Any>? = null) {
        firebaseAnalytics.logEvent(eventName, parameters)
    }
}
