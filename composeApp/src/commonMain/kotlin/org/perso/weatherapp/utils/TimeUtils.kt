package org.perso.weatherapp.utils

// Fonction attendue (expect) qui sera implémentée par chaque plateforme
expect fun convertTimestampToReadableTime(timestamp: Long): String

// Optionnellement, une implémentation par défaut
fun convertTimestampToReadableTimeDefault(timestamp: Long): String {
    // Implémentation de base si nécessaire
    val totalSeconds = timestamp
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
}