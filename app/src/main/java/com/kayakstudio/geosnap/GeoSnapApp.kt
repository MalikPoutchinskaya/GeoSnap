package com.kayakstudio.geosnap

import android.app.Application
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.soloader.SoLoader
import com.google.firebase.FirebaseApp
import com.kayakstudio.geosnap.di.androidModule
import com.kayakstudio.geosnap.indus.EnvHelper.isProduction
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.initialize
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class GeoSnapApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        AppInitializer.initialize(isDebug = true) {
            androidContext(this@GeoSnapApp)
            modules(androidModule)
        }

        // Firebase
        Firebase.initialize(this)
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(isProduction)
        Firebase.analytics.setAnalyticsCollectionEnabled(isProduction)

        // FLipper
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client by inject<FlipperClient>()
            client.start()
        }
    }
}