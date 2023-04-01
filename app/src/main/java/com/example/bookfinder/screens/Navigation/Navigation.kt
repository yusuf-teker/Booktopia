package com.example.bookfinder.screens.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookfinder.screens.categories.CategoriesScreen
import com.example.bookfinder.screens.favorites.FavoritesScreen
import com.example.bookfinder.screens.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route ){
        composable(Screen.Home.route){
            HomeScreen(navController)
        }
        composable(Screen.Favorites.route){
            FavoritesScreen(navController)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen( )
        }

    }
}


object Graph{
    const val ROOT = "root_graph"
    const val SEARCH = "search_graph"
    const val HOME = "home_graph"
    const val FAVORITES = "favorites_graph"
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Categories : Screen("categories")
    object Search : Screen("search")
    object FavoriteDetails: Screen("favorite_details")
}