package com.example.bookfinder.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.util.emailMaxLength
import com.example.bookfinder.util.passwordMaxLength

@Composable
fun SignupScreen(viewModel: AuthViewModel, onAuthScreenStateChange: (AuthScreenState) -> Unit  ) {
    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val passwordText2 = remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 32.dp)){
                Image(
                    painter = painterResource(id = R.drawable.logo_no_background), contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )

            }
            Text(
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colors.onSurface
            )
            OutlinedTextField(
                value = emailText.value,
                label = { Text(text = stringResource(id = R.string.enter_your_email)) },
                onValueChange = {
                    if (it.length < emailMaxLength)
                        emailText.value = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    disabledLabelColor = Color.Gray,
                    focusedLabelColor = MaterialTheme.colors.onSurface,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface,
                    errorBorderColor = MaterialTheme.colors.error,
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface
                ),
                maxLines = 1,
            )
            OutlinedTextField(
                value = passwordText.value,
                label = { Text(text = stringResource(id = R.string.enter_your_password)) },
                onValueChange = {
                    if (it.length < passwordMaxLength)
                        passwordText.value = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    disabledLabelColor = Color.Gray,
                    focusedLabelColor = MaterialTheme.colors.onSurface,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface,
                    errorBorderColor = MaterialTheme.colors.error,
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = passwordText2.value,
                label = { Text(text = stringResource(id = R.string.enter_your_password)) },
                onValueChange = {
                    if (it.length < passwordMaxLength)
                        passwordText2.value = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    disabledLabelColor = Color.Gray,
                    focusedLabelColor = MaterialTheme.colors.onSurface,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface,
                    errorBorderColor = MaterialTheme.colors.error,
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                )
            )
            Button(
                onClick = {
                    viewModel.signUp(email = emailText.value, passwordText.value)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    disabledBackgroundColor = Color.Gray
                )
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