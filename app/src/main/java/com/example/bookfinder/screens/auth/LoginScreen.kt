package com.example.bookfinder.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.screens.common.AppLogo
import com.example.bookfinder.screens.common.EmailTextField
import com.example.bookfinder.screens.common.PasswordTextField

@Composable
fun LoginScreen( viewModel: AuthViewModel, onAuthScreenStateChange: (AuthScreenState) -> Unit){
    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // App Logo
            AppLogo()

            // Login Title
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colors.onSurface
            )

            // Email TextField
            EmailTextField(
                emailText.value,
                onEmailTextChange = {
                    emailText.value = it
                }
            )

            // Password TextField
            PasswordTextField(
                passwordText.value,
                onPasswordTextChange = {
                    passwordText.value = it
                }
            )

            // Login Button
            Button(
                onClick = {
                    if (!emailText.value.isNullOrEmpty() && !passwordText.value.isNullOrEmpty()){
                        viewModel.login(emailText.value, passwordText.value)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    disabledBackgroundColor = Color.Gray
                )
            ) {
                Text(text = stringResource(id = R.string.login))
            }

            // Go to Signup Text+Button
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dont_have_account),
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1
                )
                TextButton(
                    onClick = {
                        onAuthScreenStateChange(AuthScreenState.Signup)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.signup),
                        color = Color.Magenta,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    LoginScreen(AuthViewModel()){

    }
}