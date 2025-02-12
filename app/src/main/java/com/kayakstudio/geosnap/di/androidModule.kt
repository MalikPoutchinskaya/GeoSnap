package com.kayakstudio.geosnap.di

import android.content.Context
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.kayakstudio.geosnap.data.Converters
import com.kayakstudio.geosnap.data.DATABASE_NAME
import com.kayakstudio.geosnap.data.GeoSnapDataBase
import com.kayakstudio.geosnap.tools.android.AndroidOpenExternalApp
import com.kayakstudio.geosnap.tools.android.OpenExternalApp
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule =
    module {
        singleOf(::AndroidOpenExternalApp) { bind<OpenExternalApp>() }

        single { NetworkFlipperPlugin() }
        single { FlipperOkhttpInterceptor(get(), true) }

        single {
            AndroidFlipperClient.getInstance(get()).apply {
                val descriptorMapping: DescriptorMapping = DescriptorMapping.withDefaults()

                addPlugin(InspectorFlipperPlugin(get(), descriptorMapping))
                addPlugin(get() as NetworkFlipperPlugin)
                addPlugin(CrashReporterPlugin.getInstance())
                addPlugin(DatabasesFlipperPlugin(get() as Context))
                addPlugin(NavigationFlipperPlugin.getInstance())
            }
        }

        single {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(get())
            SharedPreferencesSettings(sharedPrefs) as Settings
        }

        single {
            OkHttp.create {
                addInterceptor(get<FlipperOkhttpInterceptor>())
            }
        }

        singleOf(::Converters)

        single {
            val path = get<Context>().getDatabasePath(DATABASE_NAME).absolutePath
            Room
                .databaseBuilder<GeoSnapDataBase>(context = get<Context>(), name = path)
                .fallbackToDestructiveMigrationOnDowngrade(true)
                .fallbackToDestructiveMigration(true)
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }

    }
