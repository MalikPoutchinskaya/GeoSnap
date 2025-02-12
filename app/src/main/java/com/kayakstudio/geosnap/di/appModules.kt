package com.kayakstudio.geosnap.di

import com.kayakstudio.geosnap.data.GeoSnapDataBase
import com.kayakstudio.geosnap.data.apiutils.httpClient
import com.kayakstudio.geosnap.data.app.AppRepository
import com.kayakstudio.geosnap.data.app.KeyValueStorage
import com.kayakstudio.geosnap.data.app.KeyValueStorageImpl
import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelper
import com.kayakstudio.geosnap.data.auth.FirebaseAuthHelperImpl
import com.kayakstudio.geosnap.data.document.DocumentApi
import com.kayakstudio.geosnap.data.document.DocumentRepository
import com.kayakstudio.geosnap.data.document.KtorDocumentApi
import com.kayakstudio.geosnap.data.player.KtorPlayerApi
import com.kayakstudio.geosnap.data.player.PlayerApi
import com.kayakstudio.geosnap.data.player.PlayerDao
import com.kayakstudio.geosnap.data.player.PlayerRepository
import com.kayakstudio.geosnap.data.picture.KtorPlayerPictureApi
import com.kayakstudio.geosnap.data.picture.PlayerPictureApi
import com.kayakstudio.geosnap.data.picture.PlayerPictureDao
import com.kayakstudio.geosnap.data.picture.PlayerPictureRepository
import com.kayakstudio.geosnap.data.stubs.apiStubModule
import com.kayakstudio.geosnap.data.stubs.scenario.buildScenario
import com.kayakstudio.geosnap.data.user.KtorUserApi
import com.kayakstudio.geosnap.data.user.UserApi
import com.kayakstudio.geosnap.data.user.UserDao
import com.kayakstudio.geosnap.data.user.UserRepository
import com.kayakstudio.geosnap.domain.LogoutUseCase
import com.kayakstudio.geosnap.indus.analytics.Analytics
import com.kayakstudio.geosnap.indus.analytics.AnalyticsSession
import com.kayakstudio.geosnap.indus.analytics.FirebaseAnalyticsImpl
import com.kayakstudio.geosnap.indus.crashlytics.CrashlyticsSession
import com.kayakstudio.geosnap.ui.features.camera.frame.CameraScreenModel
import com.kayakstudio.geosnap.ui.features.dashboard.DashboardScreenModel
import com.kayakstudio.geosnap.ui.features.geoguesser.GeoGuesserScreenModel
import com.kayakstudio.geosnap.ui.features.login.AuthScreenModel
import com.kayakstudio.geosnap.ui.features.main.MainViewModel
import com.kayakstudio.geosnap.ui.features.profile.ProfileScreenModel
import com.kayakstudio.geosnap.ui.features.profile.aboutme.AboutMeScreenModel
import com.kayakstudio.geosnap.ui.features.profile.profileskillsselection.ProfileSkillsSelectionScreenModel
import com.kayakstudio.geosnap.ui.features.profile.settings.SettingsScreenModel
import com.kayakstudio.geosnap.ui.features.profile.updatepassword.UpdatePasswordScreenModel
import com.kayakstudio.geosnap.ui.features.profile.updateprofile.UpdateProfileScreenModel
import com.kayakstudio.geosnap.ui.features.router.RouterScreenModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.crashlytics
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val toolingModule =
    module {
        factoryOf(::CrashlyticsSession)
        factoryOf(::AnalyticsSession)
        single { Firebase.analytics }
        single { Firebase.crashlytics }
        factoryOf(::FirebaseAnalyticsImpl) { bind<Analytics>() }
    }

val screenModelsModule =
    module {
        factoryOf(::RouterScreenModel)
        factoryOf(::AuthScreenModel)
        factoryOf(::MainViewModel)
        factoryOf(::ProfileScreenModel)
        factoryOf(::SettingsScreenModel)
        factoryOf(::UpdatePasswordScreenModel)
        factoryOf(::UpdateProfileScreenModel)
        factoryOf(::AboutMeScreenModel)
        factoryOf(::ProfileSkillsSelectionScreenModel)
        factoryOf(::GeoGuesserScreenModel)
        factoryOf(::DashboardScreenModel)
        factoryOf(::CameraScreenModel)
    }

val preferenceModule =
    module {
        single { KeyValueStorageImpl(get()) as KeyValueStorage }
    }

val daoModule =
    module {
        single<UserDao> { get<GeoSnapDataBase>().userDao() }
        single<PlayerDao> { get<GeoSnapDataBase>().playerDao() }
        single<PlayerPictureDao> { get<GeoSnapDataBase>().playerPictureDao() }
    }

val authModule = module {
    singleOf(::FirebaseAuthHelperImpl) { bind<FirebaseAuthHelper>() }
}

// todo: not used at this stage
val apiModule = module {
    single {
        val json =
            Json {
                ignoreUnknownKeys = true
                // do not include "null" value
                encodeDefaults = false
            }
        val appRepository: AppRepository by inject<AppRepository>()
        val firebaseAuthHelper: FirebaseAuthHelper by inject<FirebaseAuthHelperImpl>()
        httpClient(
            isDebug = true,
            json = json,
            engine = get(),
            appRepository = appRepository,
            authHelper = firebaseAuthHelper,
        )
    }

    singleOf(::KtorUserApi) { bind<UserApi>() }
    singleOf(::KtorPlayerApi) { bind<PlayerApi>() }
    singleOf(::KtorPlayerPictureApi) { bind<PlayerPictureApi>() }
    singleOf(::KtorDocumentApi) { bind<DocumentApi>() }
}

val dataModule =
    module {
        // todo: replace apiStubModule by apiModule once BE ready
        includes(authModule, daoModule, apiStubModule)
        buildScenario()

        includes(preferenceModule)

        singleOf(::AppRepository)
        singleOf(::UserRepository)
        singleOf(::DocumentRepository)
        singleOf(::PlayerRepository)
        singleOf(::PlayerPictureRepository)
    }

val domainModule =
    module {
        includes(dataModule)
        singleOf(::LogoutUseCase)
    }

// should be at the bottom for [includes] : https://github.com/InsertKoinIO/koin/issues/1702
val appModules =
    module {
        includes(
            toolingModule,
            dataModule,
            screenModelsModule,
            domainModule
        )
    }
