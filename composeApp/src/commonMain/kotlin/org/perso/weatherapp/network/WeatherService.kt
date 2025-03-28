package org.perso.weatherapp.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.perso.weatherapp.model.WeatherData

class WeatherService {
    private val client = createHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getWeatherForCity(cityName: String): WeatherData {
        val response: WeatherApiResponse = client.get("${Config.BASE_URL}/weather") {
            parameter("q", cityName)
            parameter("appid", Config.API_KEY)
            parameter("units", "metric")
            parameter("lang", "fr")
        }.body()

        return WeatherData(
            city = cityName,
            temperature = response.main.temp,
            feelsLike = response.main.feelsLike,
            description = response.weather.first().description,
            humidity = response.main.humidity,
            windSpeed = response.wind.speed,
            iconCode = response.weather.first().icon,
            seaLevel = response.main.seaLevel,
            visibility = response.visibility,
            name = response.name,
            country = response.sys.country,
            sunrise = response.sys.sunrise,
            sunset = response.sys.sunset
        )
    }

    fun closeClient() {
        client.close()
    }

    @Serializable
    data class WeatherApiResponse(
        @SerialName("main") val main: MainData,
        @SerialName("wind") val wind: WindData,
        @SerialName("weather") val weather: List<WeatherCondition>,
        @SerialName("visibility") val visibility: Double,
        @SerialName("name") val name: String,
        @SerialName("sys") val sys: SysData
    )

    @Serializable
    data class MainData(
        @SerialName("temp") val temp: Double,
        @SerialName("feels_like") val feelsLike: Double,
        @SerialName("humidity") val humidity: Int,
        @SerialName("sea_level") val seaLevel: Int
    )

    @Serializable
    data class WindData(
        @SerialName("speed") val speed: Double
    )

    @Serializable
    data class WeatherCondition(
        @SerialName("description") val description: String,
        @SerialName("icon") val icon: String
    )

    @Serializable
    data class SysData(
        @SerialName("country") val country: String?,
        @SerialName("sunrise") val sunrise: Long,
        @SerialName("sunset") val sunset: Long
    )
}

