package com.example.bookfinder.screens.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookfinder.screens.app.bottomNavigation.BottomNavItem
import com.example.bookfinder.screens.app.bottomNavigation.BottomNavigationBar
import com.example.bookfinder.R
import com.example.bookfinder.screens.auth.AuthViewModel
import com.example.bookfinder.screens.favorites.FavoritesScreen
import com.example.bookfinder.screens.favorites.favoriteList.FavoritesScreenViewModel
import com.example.bookfinder.screens.home.HomeScreen
import com.example.bookfinder.screens.home.HomeScreenViewModel
import com.example.bookfinder.screens.search.SearchScreen
import com.example.bookfinder.ui.theme.Dimen

@Composable
fun AppScreen(viewModel: AuthViewModel,navController: NavHostController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = stringResource(R.string.home),
                        route = "home",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = stringResource(R.string.favorites),
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
                    .height(Dimen.bottomNavigationHeight)
                    .background(Color.Transparent)

            )

        }, content = {
            it
            NavHost(navController = navController, startDestination = Screen.Home.route ){
                composable(Screen.Home.route){
                    val homeViewModel = hiltViewModel<HomeScreenViewModel>()
                    HomeScreen(
                        onLogout ={ viewModel.logout() },
                        homeViewModel
                    )
                }
                composable(Screen.Favorites.route){
                    val viewModel = hiltViewModel<FavoritesScreenViewModel>()
                    FavoritesScreen(viewModel)
                }
                composable(Screen.Categories.route) {
                    SearchScreen()
                }
        }
        }
    )


}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Categories : Screen("categories")
    object SearchList : Screen("search_list")
    object SearchDetails: Screen("search_details")
    object FavoriteDetails: Screen("favorite_details")
    object Auth: Screen("auth")
    object App: Screen("app")
}