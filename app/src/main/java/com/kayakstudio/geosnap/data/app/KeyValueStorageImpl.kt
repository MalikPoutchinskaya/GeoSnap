package com.kayakstudio.geosnap.data.app

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanOrNullFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow

enum class StorageKeys {
    TOKEN,
    IS_DARK_MODE_ENABLED,
    IS_NOTIFICATIONS_ENABLED,
    ;

    val key get() = this.name
}

interface KeyValueStorage {
    var token: String?
    val observableToken: Flow<String?>
    var isDarkModeEnabled: Boolean?
    val observableIsDarkModeEnabled: Flow<Boolean?>
    var isNotificationsEnabled: Boolean?
    val observableIsNotificationsEnabled: Flow<Boolean?>

    fun cleanStorage()
}

class KeyValueStorageImpl(
    private val settings: Settings,
) : KeyValueStorage {
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    override var token: String?
        get() = settings[StorageKeys.TOKEN.key]
        set(value) {
            settings[StorageKeys.TOKEN.key] = value
        }

    @OptIn(ExperimentalSettingsApi::class)
    override val observableToken: Flow<String?>
        get() = observableSettings.getStringOrNullFlow(StorageKeys.TOKEN.key)


    override var isDarkModeEnabled: Boolean?
        get() = settings[StorageKeys.IS_DARK_MODE_ENABLED.key]
        set(value) {
            settings[StorageKeys.IS_DARK_MODE_ENABLED.key] = value
        }

    @OptIn(ExperimentalSettingsApi::class)
    override val observableIsDarkModeEnabled: Flow<Boolean?>
        get() = observableSettings.getBooleanOrNullFlow(StorageKeys.IS_DARK_MODE_ENABLED.key)

    override var isNotificationsEnabled: Boolean?
        get() = settings[StorageKeys.IS_NOTIFICATIONS_ENABLED.key]
        set(value) {
            settings[StorageKeys.IS_NOTIFICATIONS_ENABLED.key] = value
        }

    @OptIn(ExperimentalSettingsApi::class)
    override val observableIsNotificationsEnabled: Flow<Boolean?>
        get() = observableSettings.getBooleanOrNullFlow(StorageKeys.IS_NOTIFICATIONS_ENABLED.key)


    override fun cleanStorage() {
        settings.clear()
        observableSettings.clear()
    }
}
