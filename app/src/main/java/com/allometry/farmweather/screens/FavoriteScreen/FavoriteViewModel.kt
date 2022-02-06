package com.allometry.farmweather.screens.FavoriteScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allometry.farmweather.model.Favorite
import com.allometry.farmweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository) : ViewModel(){

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            weatherDbRepository.getAllFavorites().distinctUntilChanged().collect { listOfFav ->
                if(listOfFav.isNullOrEmpty()){
                    Log.d("TAG", ": Empty favs")
                }else {
                    _favList.value = listOfFav;
                    Log.d("Favs ", ": ${favList.value}")
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {  weatherDbRepository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch { weatherDbRepository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { weatherDbRepository.deleteFavorite(favorite) }
}