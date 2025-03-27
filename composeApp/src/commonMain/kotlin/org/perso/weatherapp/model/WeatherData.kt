package org.perso.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val city: String,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val iconCode: String,
    val seaLevel: Int,
    val visibility: Double,
    val name: String,
    val country: String?,
    val sunrise: Long,
    val sunset: Long

)