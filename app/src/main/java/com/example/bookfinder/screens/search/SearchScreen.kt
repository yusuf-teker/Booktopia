package com.example.bookfinder.screens.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookfinder.Navigation.Screen
import com.example.bookfinder.screens.search.searchDetail.SearchDetailScreen
import com.example.bookfinder.screens.search.searchDetail.SearchDetailViewModel
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel

@Composable
fun SearchScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SearchList.route
    ) {
        composable(Screen.SearchList.route) {
            val searchScreenViewModel = hiltViewModel<SearchScreenViewModel>()
            SearchListScreen( searchScreenViewModel, onBookClicked = {
                navController.navigate("${Screen.SearchDetails.route}/$it")
            } )
        }
        composable(
            route = "${Screen.SearchDetails.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments!!.getString("bookId") ?: ""
            val viewModel = hiltViewModel<SearchDetailViewModel>()
            SearchDetailScreen(bookId = bookId, viewModel = viewModel,
                navController = navController
            )
        }
    }

}