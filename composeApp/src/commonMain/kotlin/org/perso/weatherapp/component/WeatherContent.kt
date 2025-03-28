package org.perso.weatherapp.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.perso.weatherapp.model.WeatherData
import org.perso.weatherapp.utils.convertTimestampToReadableTime

@Composable
fun WeatherContent(data: WeatherData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Aujourd'hui",
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            WeatherInfoItem("Température", "${data.temperature.toInt()}°C")
            WeatherInfoItem("Condition nuageuse", data.description)
            WeatherInfoItem("Levé soleil", convertTimestampToReadableTime(data.sunrise))
            WeatherInfoItem("Coucher soleil", convertTimestampToReadableTime(data.sunset))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherInfoItem("Ressenti", "${data.feelsLike.toInt()}°C")
            WeatherInfoItem("Humidité", "${data.humidity}%")
            WeatherInfoItem("Vent", "${data.windSpeed} km/h")
            WeatherInfoItem("Pression atmosphérique", "${data.seaLevel} hPa")
            WeatherInfoItem("Visibilité", "${data.visibility/1000} km")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Demain",
            style = MaterialTheme.typography.h5
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


        }
    }
}