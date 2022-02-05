package com.allometry.farmweather.network

import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "/data/2.5/forecast/daily")
    suspend fun getWeather(@Query("q") query: String,
                            @Query("units") units: String = "imperial",
                            @Query("appid") appid: String = API_KEY): WeatherObject

}