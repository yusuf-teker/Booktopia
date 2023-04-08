package com.example.bookfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookfinder.Navigation.bottomNavigation.BottomNavItem
import com.example.bookfinder.Navigation.bottomNavigation.BottomNavigationBar
import com.example.bookfinder.Navigation.Navigation
import com.example.bookfinder.Navigation.Screen
import com.example.bookfinder.screens.auth.AuthScreen
import com.example.bookfinder.screens.auth.AuthViewModel
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.ui.theme.Dimen.bottomNavigationHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() //Amaç: Auth sayfası ile app'in geri kalanını ayırmak
            //Not:  ilerde logout olması için bunu app'e yollamamız gerecek
           BookFinderTheme {

               NavHost(navController, startDestination = Screen.Auth.route) {
                   composable(Screen.Auth.route) {
                       val viewModel = hiltViewModel<AuthViewModel>()
                       AuthScreen(viewModel, navController)
                   }
                   composable(Screen.App.route) {
                       val navController = rememberNavController()
                       Scaffold(
                           modifier = Modifier
                               .fillMaxSize().background(Color.Transparent),
                           bottomBar = {
                               BottomNavigationBar(
                                   items = listOf(
                                       BottomNavItem(
                                           name = getString(R.string.home),
                                           route = "home",
                                           icon = Icons.Default.Home
                                       ),
                                       BottomNavItem(
                                           name = getString(R.string.favorites),
                                           route = "favorites",
                                           icon = Icons.Default.Favorite
                                       ),
                                       BottomNavItem(
                                           name = stringResource(id = R.string.search),
                                           route = "categories",
                                           icon = Icons.Default.Search
                                       )
                                   ),
                                   navController = navController,
                                   onItemClick = {
                                       navController.navigate(it.route)
                                   },
                                   modifier = Modifier
                                       .height(bottomNavigationHeight)
                                       .background(Color.Transparent)

                               )

                           }, content = {
                               it
                               Navigation(navController)
                           }
                       )
                   }
               }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookFinderTheme {

    }
}