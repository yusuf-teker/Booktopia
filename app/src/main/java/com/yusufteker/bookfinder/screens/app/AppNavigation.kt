package com.yusufteker.bookfinder.screens.app

import android.annotation.SuppressLint
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
import com.yusufteker.bookfinder.screens.app.bottomNavigation.BottomNavItem
import com.yusufteker.bookfinder.screens.app.bottomNavigation.BottomNavigationBar
import com.yusufteker.bookfinder.R
import com.yusufteker.bookfinder.screens.auth.AuthViewModel
import com.yusufteker.bookfinder.screens.favorites.FavoritesScreen
import com.yusufteker.bookfinder.screens.favorites.favoriteList.FavoritesScreenViewModel
import com.yusufteker.bookfinder.screens.home.HomeScreen
import com.yusufteker.bookfinder.screens.search.SearchScreen
import com.yusufteker.bookfinder.ui.theme.Dimen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
            NavHost(navController = navController, startDestination = Screen.Home.route ){
                composable(Screen.Home.route){
                    HomeScreen(
                        onLogout ={ viewModel.logout() },
                    )
                }
                composable(Screen.Favorites.route){
                    val favoriteViewModel = hiltViewModel<FavoritesScreenViewModel>()
                    FavoritesScreen(favoriteViewModel)
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
    object App: Screen("app")
}