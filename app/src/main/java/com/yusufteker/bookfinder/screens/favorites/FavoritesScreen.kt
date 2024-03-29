package com.yusufteker.bookfinder.screens.favorites

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yusufteker.bookfinder.screens.app.Screen
import com.yusufteker.bookfinder.screens.favorites.favoritesDetails.FavoriteDetailsScreen
import com.yusufteker.bookfinder.screens.favorites.favoriteList.FavoritesListScreen
import com.yusufteker.bookfinder.screens.favorites.favoriteList.FavoritesScreenViewModel
import com.yusufteker.bookfinder.screens.favorites.favoritesDetails.FavoriteDetailsViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesScreenViewModel) {
    val favoritesNavHostController = rememberNavController()
    NavHost(
        navController = favoritesNavHostController,
        startDestination = Screen.Favorites.route
    ) {
        composable(Screen.Favorites.route) {
            FavoritesListScreen(
                viewModel,
                onItemClicked = { book ->
                    favoritesNavHostController.navigate("${Screen.FavoriteDetails.route}/${book.bookId}")
                }
            )
        }
        composable(
            route = "${Screen.FavoriteDetails.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<FavoriteDetailsViewModel>()
            val bookId = backStackEntry.arguments!!.getString("bookId")
            FavoriteDetailsScreen(bookId = bookId?:"0", viewModel, favoritesNavHostController)
        }
    }



}

