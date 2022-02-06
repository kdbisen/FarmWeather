package com.allometry.farmweather.data

import androidx.room.*
import com.allometry.farmweather.model.City
import com.allometry.farmweather.model.Favorite
import com.allometry.farmweather.model.Unit
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    @Query(value = "select * from fav_tbl")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("select * from fav_tbl where city = :city")
    suspend fun getFavById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("delete from fav_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(value = "select * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("delete from settings_tbl")
    suspend fun deleteAllUnits()


    @Delete
    suspend fun deleteUnit(unit: Unit)

}