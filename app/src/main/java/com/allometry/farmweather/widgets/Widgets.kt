package com.allometry.farmweather.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.allometry.farmweather.R
import com.allometry.farmweather.model.WeatherItem
import com.allometry.farmweather.model.WeatherObject
import com.allometry.farmweather.utils.formatDate
import com.allometry.farmweather.utils.formatDateTime

@Composable
fun ThisWeekWeather(weatherObject: WeatherObject) {
    Text(text = "This Week", fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(2.dp))
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        largeList(weatherObject.list)
    }

}


@Composable
fun largeList(tasks: List<WeatherItem>) {
    LazyColumn(
        modifier = Modifier.padding(2.dp),
        contentPadding = PaddingValues(1.dp)
    )
    {
        items(tasks,
            key = { task -> task.gust } // 3.
        ) { task ->
            DayWeatherRow(
                formatDate(task.dt).split(",")[0] ,
                task.weather[0].icon,
                task.weather[0].main,
                task.temp.max,
                task.temp.min
            )
        }
    }
}


@Composable
fun DayWeatherRow(
    day: String, img: String, main: String, max: Double,
    min: Double
) {
    val imageUrl = "https://openweathermap.org/img/wn/${img}.png"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 2.dp, bottom = 2.dp),
        color = Color.White,
        shape = CircleShape.copy(topEnd = CornerSize(6.dp))

    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = day, color = Color.DarkGray)
            WeatherStateImage(imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFF9800)
            ) {
                Text(main , modifier = Modifier.padding(start = 5.dp, end = 5.dp))
            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ) {
                    append("$max°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                )
                {
                    append("$min°")
                }
            })



        }
    }

}

@Composable
fun SunriseAndSunsetRow(weatherObject: WeatherObject) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatDateTime(weatherObject.list[0].sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Text(
                text = formatDateTime(weatherObject.list[0].sunset),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(20.dp)
            )
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

    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherObject.list[0].humidity} %",
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherObject.list[0].pressure} msi",
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherObject.list[0].speed} mph",
                style = MaterialTheme.typography.caption
            )
        }
    }

}
