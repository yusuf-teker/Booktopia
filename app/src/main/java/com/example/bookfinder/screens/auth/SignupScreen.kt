package com.example.bookfinder.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.screens.common.AppLogo
import com.example.bookfinder.screens.common.EmailTextField
import com.example.bookfinder.screens.common.PasswordTextField

@Composable
fun SignupScreen(viewModel: AuthViewModel, onAuthScreenStateChange: (AuthScreenState) -> Unit  ) {
    val emailText = remember { mutableStateOf("") }
    val passwordValidationState = viewModel.passwordValidationState.collectAsState()
    val emailValidationState = viewModel.emailValidationState.collectAsState()
    val passwordText = remember { mutableStateOf("") }
    val passwordText2 = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppLogo()
            Text(
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colors.onSurface
            )

            EmailTextField(
                emailText.value,
                emailValidationState.value,
                onEmailTextChange = {
                    emailText.value = it
                    viewModel.setEmailValidationState(true)
                }
            )
            PasswordTextField(
                passwordText.value,
                passwordValidationState.value,
                onPasswordTextChange = {
                    passwordText.value = it
                    viewModel.setPasswordValidationState(true)
                },

            )
            PasswordTextField(
                passwordText2.value,
                passwordValidationState.value,
                onPasswordTextChange = {
                    passwordText2.value = it
                    viewModel.setPasswordValidationState(true)
                }
            )
            Button(
                onClick = {
                        viewModel.signUp(email = emailText.value, passwordText.value)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    disabledBackgroundColor = Color.Gray
                ),
            ) {
                Text(text = stringResource(id = R.string.signup))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.do_you_have_account),
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.body1
                )
                TextButton(
                    onClick = {
                       onAuthScreenStateChange(AuthScreenState.Login)
                        viewModel.setPasswordValidationState(true)
                        viewModel.setPasswordValidationState(true)
                        viewModel.setPasswordText("")
                        viewModel.setEmailText("")
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = Color.Magenta,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

        }
    }
}