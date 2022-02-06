package com.allometry.farmweather.screens.SettingScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.allometry.farmweather.model.Unit
import com.allometry.farmweather.widgets.WeatherAppBar


@Composable
fun SettingScreen(
    navController: NavController,
    settingsViewModel: SettignsViewModel = hiltViewModel()
) {

    var unitToggleState by remember { mutableStateOf(false) }
    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultChoice = if(choiceFromDb.isEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    var choiceState by remember { mutableStateOf(defaultChoice) }

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            isMainScreen = false,
            title = "Settings",
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(text = "Change Units of Measurement ", modifier = Modifier.padding(15.dp))

                IconToggleButton(
                    checked = !unitToggleState, onCheckedChange = {

                        unitToggleState = !it
                        choiceState = if (unitToggleState) {
                            "Imperial (F)"
                        } else {
                            "Metric (C)"
                        }
                        Log.d("TAG", "MainContent: $unitToggleState")
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            Color.Red.copy(0.2f)
                        )
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC" )
                }
                Button(
                    onClick = {
                        settingsViewModel.deleteALlUnits()
                        settingsViewModel.insertUnit(Unit(unit = choiceState))

                    },
                    modifier = Modifier.align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(text = "Save", Modifier.padding(4.dp), color = Color.White)

                }
            }

        }

    }
}