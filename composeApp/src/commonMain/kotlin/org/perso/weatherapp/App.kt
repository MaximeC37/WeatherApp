package org.perso.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import org.perso.weatherapp.model.WeatherData
import org.perso.weatherapp.utils.convertTimestampToReadableTime
import org.perso.weatherapp.viewmodel.WeatherUiState
import org.perso.weatherapp.viewmodel.WeatherViewModel

@Composable
fun App() {
    val viewModel = remember { WeatherViewModel() }
    val weatherState by viewModel.weatherState.collectAsState()

    var cityInput by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = cityInput,
                    onValueChange = { cityInput = it },
                    label = { Text("Ville") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { viewModel.searchCity(cityInput) }) {
                    Text("Rechercher")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = weatherState) {
                is WeatherUiState.Empty -> {
                    Text("Recherchez une ville")
                }
                is WeatherUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is WeatherUiState.Success -> {
                    WeatherContent(state.data)
                }
                is WeatherUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

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

@Composable
fun WeatherInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.caption)
        Text(text = value, style = MaterialTheme.typography.body1)
    }
}