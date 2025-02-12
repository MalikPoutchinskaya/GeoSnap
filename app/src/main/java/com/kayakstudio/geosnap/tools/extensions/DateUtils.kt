package com.kayakstudio.geosnap.tools.extensions

import com.raedghazal.kotlinx_datetime_ext.LocalDateTimeFormatter
import com.raedghazal.kotlinx_datetime_ext.Locale
import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toReadableDayMonth(): String {
    val myFormat = LocalDateTimeFormatter.ofPattern("dd MMMM", Locale.default())
    return myFormat.format(this)
}