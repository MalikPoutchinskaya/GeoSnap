package com.kayakstudio.geosnap.indus

import com.kayakstudio.geosnap.BuildConfig

object EnvHelper {
    val isProduction = BuildConfig.DEBUG.not()
    val isMock = false
    val isMockWithServer = false
}