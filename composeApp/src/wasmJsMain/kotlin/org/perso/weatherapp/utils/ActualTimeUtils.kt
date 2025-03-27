package org.perso.weatherapp.utils

actual fun convertTimestampToReadableTime(timestamp: Long): String {
    // Pour WASM, une implémentation simple
    val hours = (timestamp / 3600).toInt()
    val minutes = ((timestamp % 3600) / 60).toInt()
    return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
}