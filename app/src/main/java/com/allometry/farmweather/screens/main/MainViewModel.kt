package com.allometry.farmweather.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allometry.farmweather.data.DataOrException
import com.allometry.farmweather.model.City
import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {


    suspend fun getWeatherData(city: String, unit: String) : DataOrException<WeatherObject, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, unit = unit)
    }




}