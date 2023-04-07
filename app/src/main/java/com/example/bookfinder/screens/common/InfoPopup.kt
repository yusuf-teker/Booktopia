package com.example.bookfinder.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookfinder.R

@Composable
fun InfoPopup(onDismiss: () -> Unit, onNeverShowAgain: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.title_how_to_add_favorite),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface

            )
        },
        text = {
            Text(
                text = stringResource(R.string.info_message_how_to_add_favorite),
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface
            )
        },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onNeverShowAgain()
                    }
                ) {
                    Text(stringResource(R.string.dont_show_again), color = Color.Red)
                }
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.okay), color = MaterialTheme.colors.onSurface)
                }
            }
        },
        shape = RoundedCornerShape(32.dp),
        backgroundColor = MaterialTheme.colors.surface
    )
}
