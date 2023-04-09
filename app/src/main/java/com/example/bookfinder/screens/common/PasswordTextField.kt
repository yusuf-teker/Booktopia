package com.example.bookfinder.screens.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bookfinder.R
import com.example.bookfinder.util.passwordMaxLength

@Composable
fun PasswordTextField(
    passwordText: String,
    passwordValidationState: Boolean,
    onPasswordTextChange: (String) -> Unit,
) {


    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = passwordText,
        label = { Text(text = stringResource(R.string.enter_your_password)) },
        onValueChange = {
            if (it.length < passwordMaxLength)
                onPasswordTextChange(it)
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
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (passwordVisible.value) "Hide password" else "Show password"
            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector  = image, description)
            }
        },
        isError = !passwordValidationState
    )
}