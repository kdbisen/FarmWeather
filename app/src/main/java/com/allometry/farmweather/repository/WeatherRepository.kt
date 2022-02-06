package com.allometry.farmweather.repository

import android.util.Log
import com.allometry.farmweather.data.DataOrException
import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeather(cityQuery: String, unit: String): DataOrException<WeatherObject, Boolean, Exception> {
        val response = try {
              weatherApi.getWeather(query = cityQuery, units = unit)
        }
        catch (e: Exception)
        {
            Log.d("Exception - ", "getWeather Exception: $e")
            return  DataOrException(e = e)

        }
        return DataOrException(data = response)
    }

}