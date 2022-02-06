package com.allometry.farmweather.screens.SettingScreen



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allometry.farmweather.model.Favorite
import com.allometry.farmweather.model.Unit
import com.allometry.farmweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettignsViewModel @Inject constructor(private val weatherDbRepository: WeatherDbRepository) : ViewModel(){

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            weatherDbRepository.getUnits().distinctUntilChanged().collect { listOfFav ->
                if(listOfFav.isNullOrEmpty()){
                    Log.d("TAG", ": Empty Uinits")
                }else {
                    _unitList.value = listOfFav;
                    Log.d("Favs ", ": ${unitList.value}")
                }
            }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {  weatherDbRepository.insertUnit(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { weatherDbRepository.updateUnit(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { weatherDbRepository.deleteUnit(unit) }
    fun deleteALlUnits( ) = viewModelScope.launch { weatherDbRepository.deleteAllUNits() }
}