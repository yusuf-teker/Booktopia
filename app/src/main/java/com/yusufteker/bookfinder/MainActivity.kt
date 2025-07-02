package com.yusufteker.bookfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.yusufteker.bookfinder.screens.app.AppScreen
import com.yusufteker.bookfinder.screens.auth.AuthScreen
import com.yusufteker.bookfinder.screens.auth.AuthViewModel
import com.yusufteker.bookfinder.ui.theme.BookFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
           BookFinderTheme {
               val viewModel = hiltViewModel<AuthViewModel>()
               val isLoggedIn by viewModel.isLoggedIn.collectAsState()

               if (isLoggedIn) {
                   AppScreen(viewModel,navController)
               } else {
                   AuthScreen(viewModel, navController)
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