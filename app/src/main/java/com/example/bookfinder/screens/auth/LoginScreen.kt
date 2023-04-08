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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.util.emailMaxLength
import com.example.bookfinder.util.passwordMaxLength


@Composable
fun LoginScreen( viewModel: AuthViewModel, onAuthScreenStateChange: (AuthScreenState) -> Unit){
    val emailText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }



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
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colors.onSurface
            )

            OutlinedTextField(
                value = emailText.value,
                label = { Text(text = stringResource(R.string.enter_your_email)) },
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
                )
            )

            OutlinedTextField(
                value = passwordText.value,
                label = { Text(text = stringResource(R.string.enter_your_password)) },
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
            
            Button(
                onClick = {
                    viewModel.login(emailText.value, passwordText.value)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    disabledBackgroundColor = Color.Gray
                )
            ) {
                Text(text = stringResource(id = R.string.login))
            }
            
            
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