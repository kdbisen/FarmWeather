package com.allometry.farmweather.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.allometry.farmweather.model.Favorite
import com.allometry.farmweather.navigation.WeatherScreens
import com.allometry.farmweather.screens.FavoriteScreen.FavoriteViewModel

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showMoreDialog = remember { mutableStateOf(false) }
    val showIt = remember {  mutableStateOf(false) }
    val toastMessage = remember {  mutableStateOf("") }

    val context = LocalContext.current

    if (showMoreDialog.value) {
        ShowSettingDropDownMenu(showMoreDialog = showMoreDialog, navController = navController)
    }
    TopAppBar(title = {
        Text(
            text = title, color = Color.LightGray,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { onAddActionClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }
            IconButton(onClick = {
                showMoreDialog.value = true
            }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Search Icon")
            }
        } else {
            Box() {}
        }

    }, navigationIcon = {
        if (icon != null) {
            Icon(imageVector = icon,
                contentDescription = "Navigation Icon",
                tint = Color.LightGray,
                modifier = Modifier.clickable {
                    onButtonClicked.invoke()
                })
        }

        if(isMainScreen) {
                val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter { item ->
                 (item.city == title.split(",")[0])
                }

            if (isAlreadyFavList.isEmpty()) {
                Icon(imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "FavoriteBorder Icon",
                    tint = Color.Red.copy(alpha = 0.9f),
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            favoriteViewModel.insertFavorite(
                                Favorite(
                                    city = title.split(",")[0],
                                    country = title.split(",")[1]
                                )
                            ).run {
                                toastMessage.value = "City "+title.split(",")[0]+" added to favorite"
                                showIt.value = true;
                            }

                         })
            }
            else  {
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "FavoriteBorder Icon",
                    tint = Color.Red.copy(alpha = 0.6f),
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            favoriteViewModel.deleteFavorite(
                                Favorite(
                                    city = title.split(",")[0],
                                    country = title.split(",")[1]
                                )
                            ).run {
                                toastMessage.value = "City "+title.split(",")[0]+" removed from favorite"
                                showIt.value = true;
                            }
                        })
            }
            ShowToad(context = context, showIt = showIt , toastMessage.value)


        }
    }, backgroundColor = Color.Transparent, elevation = elevation)
}

@Composable
fun ShowToad(context: Context, showIt: MutableState<Boolean>, toastMessage: String) {
    if(showIt.value){
        Toast.makeText(context, toastMessage,Toast.LENGTH_SHORT ).show()
    }

}

@Composable
fun ShowSettingDropDownMenu(showMoreDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf<String>("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 10.dp)
    ) {
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {

            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showMoreDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings

                        }, contentDescription = null,
                        tint = Color.LightGray
                    )


                    Text(text = text, modifier = Modifier.clickable {
                        navController.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingScreen.name
                            }
                        )

                    }, fontWeight = FontWeight.W300, color = Color.DarkGray)
                }
            }
        }
    }
}
