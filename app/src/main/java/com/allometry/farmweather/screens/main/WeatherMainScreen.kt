package com.allometry.farmweather.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.allometry.farmweather.R
import com.allometry.farmweather.data.DataOrException
import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.screens.main.MainViewModel
import com.allometry.farmweather.utils.formatDate
import com.allometry.farmweather.utils.formatDateTime
import com.allometry.farmweather.utils.formatDecimals
import com.allometry.farmweather.widgets.WeatherAppBar

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val weatherData = produceState<DataOrException<WeatherObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("moscow")
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
            icon = Icons.Default.ArrowBack
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
        SunriseAndSunsetRow(weatherObject = data);
    }
}

@Composable
fun SunriseAndSunsetRow(weatherObject: WeatherObject) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier
            .padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "Sunrise icon",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weatherObject.list[0].sunrise), style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier
            .padding(4.dp)) {
            Text(text =  formatDateTime(weatherObject.list[0].sunset), style = MaterialTheme.typography.caption)
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "Sunset icon",
                modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl), contentDescription = "Icon Image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weatherObject: WeatherObject) {
    
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween) {
        
        Row(modifier = Modifier
            .padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity icon", 
            modifier = Modifier.size(20.dp))
            Text(text = "${weatherObject.list[0].humidity} %", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier
            .padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherObject.list[0].pressure} msi", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier
            .padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherObject.list[0].speed} mph", style = MaterialTheme.typography.caption)
        }
    }

}
