package com.yusufteker.bookfinder.screens.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.yusufteker.bookfinder.R
import com.yusufteker.bookfinder.util.emailMaxLength

@Composable
fun EmailTextField(
    emailText: String,
    emailValidationState: Boolean,
    onEmailTextChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = emailText,
        label = { Text(text = stringResource(id = R.string.enter_your_email)) },
        onValueChange = {
            if (it.length < emailMaxLength){
                onEmailTextChange(it)
            }

        },
        colors =
            TextFieldDefaults.outlinedTextFieldColors(
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
        isError = !emailValidationState
    )
}