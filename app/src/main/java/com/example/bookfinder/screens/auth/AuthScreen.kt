package com.example.bookfinder.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.bookfinder.screens.app.Screen


@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    val isLoading = viewModel.isLoading.collectAsState()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()

    val authScreenState = remember{
        mutableStateOf(AuthScreenState.Login)
    }
    if (isLoggedIn.value){
        navController.navigate(Screen.App.route)
    }

    if (isLoading.value){
        Box(modifier = Modifier.background(Color.Transparent).fillMaxSize()) {
            CircularProgressIndicator(
                color = Color.Green,
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }else{
        when(authScreenState.value){
            AuthScreenState.Login ->  LoginScreen(
                viewModel,
                onAuthScreenStateChange = { authScreenState.value = it }
            )
            AuthScreenState.Signup -> SignupScreen(viewModel){
                authScreenState.value = it
            }
            AuthScreenState.ForgotPassword -> ForgotPasswordScreen{
                authScreenState.value = it
            }
        }

    }

}

enum class AuthScreenState {
    Login,
    Signup,
    ForgotPassword
}