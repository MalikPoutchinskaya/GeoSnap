package com.kayakstudio.geosnap.indus.analytics


sealed class AnalyticsEvent {

    // User Interaction Events
    data object UserSignupInitiated : AnalyticsEvent()
    data object UserSignupCompleted : AnalyticsEvent()
    data object UserLoginInitiated : AnalyticsEvent()
    data object UserLoginSuccess : AnalyticsEvent()
    data object UserLogout : AnalyticsEvent()


    enum class FrequencyType {
        WEEKLY, MONTHLY
    }

    // Button Click Events
    data object UniverseButtonClicked : AnalyticsEvent()
    data object PlaceAnOrderButtonClicked : AnalyticsEvent()
    data object LogoutButtonClicked : AnalyticsEvent()
    data object DeleteAccountButtonClicked : AnalyticsEvent()
    data object ManualEditQuantityButtonClicked : AnalyticsEvent()
    data object LongPressQuantityButtonClicked : AnalyticsEvent()

    // Feature Interaction Events
    data class LoyaltyPointsEarned(val ruleType: RuleType) : AnalyticsEvent()
    data object LoyaltyPointsRedeemed : AnalyticsEvent()
    data class ProductViewed(val productId: String) : AnalyticsEvent()
    data object ChildAccountAdded : AnalyticsEvent()
    data object ChildAccountDeleted : AnalyticsEvent()

    enum class RuleType {
        FIRST_FIVE_ORDERS, WEEKLY_ORDER, MONTHLY_ORDER, ORDER_AMOUNT_BASED
    }

    // Screen Navigation Events
    data class ScreenView(val screenName: ScreenName) : AnalyticsEvent()
    data object SessionDuration : AnalyticsEvent()
    data object BounceRate : AnalyticsEvent()

    enum class ScreenName {
        LOGIN_PHONE_NUMBER, LOGIN_OTP, CART, CHECKOUT, PLACE_AN_ORDER, PRODUCT_DETAILS, PRODUCTS_CATALOG, SEND_ORDER, ORDER_DETAILS, ORDERS_HISTORY, POLICY_AGREEMENT, PROMOTIONS_ADS, STORIES, HOME, INSIGHTS, ACCOUNT, CHILD_ACCOUNT, EDIT_USER_INFO, SCAN
    }

    // Checkout & Payment Events
    data object CheckoutInitiated : AnalyticsEvent()
    data class PaymentMethodSelected(val paymentType: PaymentType) : AnalyticsEvent()
    data object DiscountApplied : AnalyticsEvent()
    data object CheckoutCompleted : AnalyticsEvent()
    data object CheckoutAbandoned : AnalyticsEvent()

    enum class PaymentType {
        CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER
    }

    // Loyalty Program Events
    data class LoyaltyPointsEarnedForOrder(val orderId: String, val points: Int) : AnalyticsEvent()
    data object LoyaltyPointsBalanceViewed : AnalyticsEvent()
    data class LoyaltyEngagement(val engagementType: EngagementType) : AnalyticsEvent()

    enum class EngagementType {
        WEEKLY, MONTHLY
    }
}
