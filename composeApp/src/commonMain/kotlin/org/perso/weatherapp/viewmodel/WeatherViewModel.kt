package org.perso.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.perso.weatherapp.model.WeatherData
import org.perso.weatherapp.network.WeatherService

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(val data: WeatherData) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    data object Empty : WeatherUiState()
}

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    private val weatherService = WeatherService()

    fun searchCity(cityName: String) {
        viewModelScope.launch {
            try {
                _weatherState.value = WeatherUiState.Loading
                val weatherData = weatherService.getWeatherForCity(cityName)
                _weatherState.value = WeatherUiState.Success(weatherData)
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error("Erreur: ${e.message}")
            }
        }
    }
}