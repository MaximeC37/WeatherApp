package org.perso.weatherapp.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

actual fun convertTimestampToReadableTime(timestamp: Long): String {
    // Utiliser kotlinx.datetime qui est mieux supporté en Wasm
    val instant = Instant.fromEpochSeconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    // Formater l'heure manuellement (HH:mm)
    val hours = localDateTime.hour.toString().padStart(2, '0')
    val minutes = localDateTime.minute.toString().padStart(2, '0')

    return "$hours:$minutes"
}
