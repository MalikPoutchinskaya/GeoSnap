package com.kayakstudio.geosnap.data.stubs

import com.kayakstudio.geosnap.data.document.DocumentApi
import com.kayakstudio.geosnap.data.player.PlayerApi
import com.kayakstudio.geosnap.data.picture.PlayerPictureApi
import com.kayakstudio.geosnap.data.stubs.datasources.DocumentApiStub
import com.kayakstudio.geosnap.data.stubs.datasources.PlayerApiStub
import com.kayakstudio.geosnap.data.stubs.datasources.PlayerPictureApiStub
import com.kayakstudio.geosnap.data.stubs.datasources.SettingsStub
import com.kayakstudio.geosnap.data.stubs.datasources.UserApiStub
import com.kayakstudio.geosnap.data.stubs.datasources.UserDaoStub
import com.kayakstudio.geosnap.data.user.UserApi
import com.kayakstudio.geosnap.data.user.UserDao
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsStubs = module {
    single { SettingsStub() as Settings }
}
val apiStubModule = module {
    singleOf(::UserApiStub) { bind<UserApi>() }
    singleOf(::PlayerApiStub) { bind<PlayerApi>() }
    singleOf(::PlayerPictureApiStub) { bind<PlayerPictureApi>() }
    singleOf(::DocumentApiStub) { bind<DocumentApi>() }
}

val daoStubs = module {
    singleOf(::UserDaoStub) { bind<UserDao>() }
}