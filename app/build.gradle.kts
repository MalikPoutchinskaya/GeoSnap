plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    id("com.google.firebase.appdistribution")
    id("org.ajoberstar.grgit")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.kayakstudio.geosnap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kayakstudio.geosnap"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.ui.text.google.fonts)
    ksp(libs.androidx.room.compiler)
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3IconsExtended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // firebase
    implementation(libs.firebase.kmp.auth)
    api(libs.firebase.crashlytics)
    api(libs.firebase.analytics)

    // permissions
    implementation (libs.accompanist.permissions)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // database
    implementation(libs.androidx.room.runtime)
    implementation(libs.sqlite.bundled)

    // key-value cache
    implementation(libs.multiplatform.settings)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.multiplatform.settings.coroutines)
    implementation(libs.multiplatform.settings.serialization)

    // logs
    implementation(libs.kermit)

    // date & time
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.datetime.ext)

    // maps
    implementation(libs.maps.compose)

    // mime type
    implementation(libs.okio)

    // di
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    // http calls
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    // navigation
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel)
    implementation(libs.voyager.bottom.sheet.navigator)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.koin)

    // image loader
    implementation(libs.kamel.image)

    // lottie
    implementation(libs.compottie)

    // konfetti
    implementation(libs.konfetti.compose)

    // picture
    implementation(libs.peekaboo.ui)
    implementation(libs.peekaboo.image.picker)

    // file
    implementation(libs.filekit.core)
    implementation(libs.filekit.compose)

    // pagination
    implementation(libs.paging.compose.common)

    // bottomsheet
    implementation(libs.flexible.bottomsheet.material3)

    // country code parsing
    implementation(libs.libphonenumber)

    // font
    implementation(libs.androidx.ui.text.google.fonts)

    // okhttp client
    implementation(libs.ktor.client.okhttp)

    // DI
    implementation(libs.koin.android)

    // debug tooling
    // todo replace the 2 [implementation] to [debugImplementation]
    implementation(libs.flipper)
    implementation(libs.flipper.network.plugin)
    implementation(libs.soloader)
}