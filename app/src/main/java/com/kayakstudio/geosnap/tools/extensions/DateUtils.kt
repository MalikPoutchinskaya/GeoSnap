package com.kayakstudio.geosnap.tools.extensions

import com.raedghazal.kotlinx_datetime_ext.LocalDateTimeFormatter
import com.raedghazal.kotlinx_datetime_ext.Locale
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

object DateUtils {
    const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun getReadableDateTime(instant: Instant): String {
        val datetimeInSystemZone: LocalDateTime =
            instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${datetimeInSystemZone.dayOfMonth} ${datetimeInSystemZone.month.name} ${datetimeInSystemZone.year}"
    }


    fun getLocalDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())


}

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = LocalDateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN, Locale.default())
    return formatter.parseToLocalDateTime(this)
}

fun String.toDuration(): Duration {
    return Duration.parse(this)
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this)
}

fun LocalDateTime.toServerFormat(): String {
    val formatter = LocalDateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN, Locale.default())
    return formatter.format(this)
}

fun LocalDateTime.toReadableDate(): String {
    val myFormat = LocalDateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.default())
    return myFormat.format(this)
}

fun LocalDateTime.toReadableDayMonth(): String {
    val myFormat = LocalDateTimeFormatter.ofPattern("dd MMMM", Locale.default())
    return myFormat.format(this)
}

fun LocalDateTime.toReadableDateTime(): String {
    val myFormat = LocalDateTimeFormatter.ofPattern("dd MMMM yyyy - HH:mm", Locale.default())
    return myFormat.format(this)
}

fun LocalDateTime.toReadableTime(): String {
    return "${this.hour}h${this.minute}"
}

fun LocalDate.toReadableDate(): String {
    val myFormat = LocalDateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.default())
    return myFormat.format(this)
}


fun Duration.toReadableDuration(): String {
    // Extract hours, minutes, and seconds
    val hours = this.inWholeHours
    val minutes = this.minus(hours.hours).inWholeMinutes
    val seconds = this.minus(hours.hours).minus(minutes.minutes).inWholeSeconds

    // Create a readable format
//    val readableFormat = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    return "${hours}h"
}