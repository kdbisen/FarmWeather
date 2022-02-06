package com.allometry.farmweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.allometry.farmweather.screens.AboutScreen.AboutScreen
import com.allometry.farmweather.screens.FavoriteScreen.FavoriteScreen
import com.allometry.farmweather.screens.MainScreen
import com.allometry.farmweather.screens.SettingScreen.SettingScreen
import com.allometry.farmweather.screens.WeatherSplashScreen
import com.allometry.farmweather.screens.main.MainViewModel
import com.allometry.farmweather.screens.search.SearchScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController();
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name  ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}", arguments = listOf(
            navArgument("city" ) {
                type = NavType.StringType
            }
        ))  { navBack ->
             navBack.arguments?.getString("city").let { city ->
                 val mainViewModel = hiltViewModel<MainViewModel>()
                 MainScreen(navController = navController, mainViewModel, city = city)
             }
        }

        composable(WeatherScreens.SearchScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.FavoriteScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            FavoriteScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            SettingScreen(navController = navController)
        }




    }

}