package com.allometry.farmweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allometry.farmweather.model.Favorite
import com.allometry.farmweather.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao() : WeatherDao;

}