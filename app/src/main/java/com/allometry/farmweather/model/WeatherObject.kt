package com.allometry.farmweather.model

data class WeatherObject(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)