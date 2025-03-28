package org.perso.weatherapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.perso.weatherapp.component.WeatherContent
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