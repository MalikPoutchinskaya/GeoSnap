package com.kayakstudio.geosnap

import com.kayakstudio.geosnap.di.appModules
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object AppInitializer {

    fun initialize(isDebug: Boolean = true, initPlatformModules: KoinApplication.() -> Unit) {
        if (isDebug) initializeForDebugBuilds()
        startKoin {
            initPlatformModules()
            modules(appModules)
        }
    }

    private fun initializeForDebugBuilds() {

    }
}