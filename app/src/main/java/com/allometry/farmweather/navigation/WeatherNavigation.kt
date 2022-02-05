package com.allometry.farmweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.allometry.farmweather.screens.WeatherMainScreen
import com.allometry.farmweather.screens.WeatherSplashScreen
import com.allometry.farmweather.screens.main.MainViewModel


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController();
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name  ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            WeatherMainScreen(navController = navController, mainViewModel)
        }

    }

}