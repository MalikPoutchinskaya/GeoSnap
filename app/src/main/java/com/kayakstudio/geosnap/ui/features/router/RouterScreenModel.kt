package com.kayakstudio.geosnap.ui.features.router

import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.indus.analytics.AnalyticsSession
import com.kayakstudio.geosnap.indus.crashlytics.CrashlyticsSession
import com.kayakstudio.geosnap.ui.tools.mvi.MviScreenModel
import kotlinx.coroutines.launch

class RouterScreenModel(
    private val userRepository: UserRepository,
    private val analyticsSession: AnalyticsSession,
    private val crashlyticsSession: CrashlyticsSession,
) : MviScreenModel<RouterContract.Event, RouterContract.State, RouterContract.Effect>() {
    override fun createInitialState(): RouterContract.State = RouterContract.State

    override fun handleEvent(event: RouterContract.Event) {}

    init {
        observeTokens()
    }

    /**
     * Observe all tokens required for login
     */
    private fun observeTokens() {
        screenModelScope.launch {
            val userObs = userRepository.observeUser()

            userObs.collect { user ->
                when {
                    user == null -> {
                        Logger.d("The token is null")
                        setEffect { RouterContract.Effect.NavigateToLoginScreen }
                    }

                    else -> {
                        Logger.d("The user and clients are present")
                        initUserSession()
                        setEffect { RouterContract.Effect.NavigateToHomeScreen }
                    }
                }
            }
        }
    }

    /**
     * init the Analytics and Crashlytics sessions
     */
    private fun initUserSession() {
        screenModelScope.launch { analyticsSession.initialize() }
        screenModelScope.launch { crashlyticsSession.initialize() }
    }

}
