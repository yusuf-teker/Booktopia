package com.example.bookfinder.screens.Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookfinder.screens.favorites.FavoritesScreen
import com.example.bookfinder.screens.favorites.favoriteList.FavoritesScreenViewModel
import com.example.bookfinder.screens.home.HomeScreen
import com.example.bookfinder.screens.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route ){
        composable(Screen.Home.route){
            HomeScreen(navController)
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

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Categories : Screen("categories")
    object SearchList : Screen("search_list")
    object SearchDetails: Screen("search_details")
    object FavoriteDetails: Screen("favorite_details")
}