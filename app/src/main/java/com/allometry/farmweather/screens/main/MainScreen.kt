package com.allometry.farmweather.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.allometry.farmweather.data.DataOrException
import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.navigation.WeatherScreens
import com.allometry.farmweather.screens.main.MainViewModel
import com.allometry.farmweather.utils.formatDate
import com.allometry.farmweather.utils.formatDecimals
import com.allometry.farmweather.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {

    val weatherData = produceState<DataOrException<WeatherObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city!!)
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        Text(text = "Main Screen ${weatherData.data!!.city.name}")
        MainScaffold(weatherObject = weatherData.data!!, navController = navController)
    }

}

@Composable
fun MainScaffold(weatherObject: WeatherObject, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weatherObject.city.name + ", ${weatherObject.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            icon = Icons.Default.ArrowBack,
            isMainScreen = true
        ) {
            Log.d("TAG -1 ", "MainScaffold: Button clicked")
        }
    }) {
        MainContent(data = weatherObject)
    }
}


@Composable
fun MainContent(data: WeatherObject) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(data.list[0].dt), style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFF9800)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //IMage
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(data.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(data.list[0].weather[0].main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weatherObject = data)
        Divider()
        SunriseAndSunsetRow(weatherObject = data)
        ThisWeekWeather(weatherObject = data)
    }
}

