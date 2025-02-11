package com.kayakstudio.geosnap.data

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json

/**
 * Room converters
 */
class Converters {
    //
    // Strings
    // =========================================================

    @TypeConverter
    fun toObjectList(input: String?): List<String> =
        input?.let { Json.decodeFromString(input) } ?: emptyList()

    @TypeConverter
    fun toString(input: List<String>?): String? = input?.let { Json.encodeToString(input) }

    //
    // LocalDateTime
    // =========================================================

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? = date?.toString()

    //
    // LocalDate
    // =========================================================

    @TypeConverter
    fun fromTimestampToLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): String? = date?.toString()

}