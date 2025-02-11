package com.kayakstudio.geosnap.data.stubs.datasources

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SettingsListener
import com.kayakstudio.geosnap.data.stubs.databases.StubLocalDatabase.settingsData

class DummySettingsListener() : SettingsListener {
    override fun deactivate() {

    }

}

class SettingsStub : ObservableSettings {

    override val keys: Set<String>
        get() = settingsData.keys

    override val size: Int
        get() = settingsData.size

    override fun addBooleanListener(
        key: String,
        defaultValue: Boolean,
        callback: (Boolean) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addBooleanOrNullListener(
        key: String,
        callback: (Boolean?) -> Unit,
    ): SettingsListener {
        return DummySettingsListener()
    }

    override fun addDoubleListener(
        key: String,
        defaultValue: Double,
        callback: (Double) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addDoubleOrNullListener(
        key: String,
        callback: (Double?) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addFloatListener(
        key: String,
        defaultValue: Float,
        callback: (Float) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addFloatOrNullListener(key: String, callback: (Float?) -> Unit): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addIntListener(
        key: String,
        defaultValue: Int,
        callback: (Int) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addIntOrNullListener(key: String, callback: (Int?) -> Unit): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addLongListener(
        key: String,
        defaultValue: Long,
        callback: (Long) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addLongOrNullListener(key: String, callback: (Long?) -> Unit): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addStringListener(
        key: String,
        defaultValue: String,
        callback: (String) -> Unit,
    ): SettingsListener {
        TODO("Not yet implemented")
    }

    override fun addStringOrNullListener(
        key: String,
        callback: (String?) -> Unit,
    ): SettingsListener {
        return DummySettingsListener()
    }

    override fun clear() {
        settingsData.clear()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settingsData[key] as? Boolean ?: defaultValue
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return settingsData[key] as? Boolean
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return settingsData[key] as? Double ?: defaultValue
    }

    override fun getDoubleOrNull(key: String): Double? {
        return settingsData[key] as? Double
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return settingsData[key] as? Float ?: defaultValue
    }

    override fun getFloatOrNull(key: String): Float? {
        return settingsData[key] as? Float
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return settingsData[key] as? Int ?: defaultValue
    }

    override fun getIntOrNull(key: String): Int? {
        return settingsData[key] as? Int
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return settingsData[key] as? Long ?: defaultValue
    }

    override fun getLongOrNull(key: String): Long? {
        return settingsData[key] as? Long
    }

    override fun getString(key: String, defaultValue: String): String {
        return settingsData[key] as? String ?: defaultValue
    }

    override fun getStringOrNull(key: String): String? {
        return settingsData[key] as? String
    }

    override fun hasKey(key: String): Boolean {
        return settingsData.containsKey(key)
    }

    override fun putBoolean(key: String, value: Boolean) {
        settingsData[key] = value
    }

    override fun putDouble(key: String, value: Double) {
        settingsData[key] = value
    }

    override fun putFloat(key: String, value: Float) {
        settingsData[key] = value
    }

    override fun putInt(key: String, value: Int) {
        settingsData[key] = value
    }

    override fun putLong(key: String, value: Long) {
        settingsData[key] = value
    }

    override fun putString(key: String, value: String) {
        settingsData[key] = value
    }

    override fun remove(key: String) {
        settingsData.remove(key)
    }
}
