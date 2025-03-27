package org.perso.weatherapp.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

actual fun convertTimestampToReadableTime(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val formatter = DateTimeFormatter
        .ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}