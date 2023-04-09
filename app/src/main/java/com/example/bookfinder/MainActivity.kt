package com.example.bookfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.bookfinder.screens.app.AppScreen
import com.example.bookfinder.screens.auth.AuthScreen
import com.example.bookfinder.screens.auth.AuthViewModel
import com.example.bookfinder.ui.theme.BookFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
           BookFinderTheme {
               val viewModel = hiltViewModel<AuthViewModel>()
               val isLoggedIn by viewModel.isLoggedIn.collectAsState()

               if (isLoggedIn) {
                   AppScreen(navController)
               } else {
                   //navController.clearBackStack(Screen.Auth.route)
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