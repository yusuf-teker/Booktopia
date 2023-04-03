package com.example.bookfinder.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoPopup(onDismiss: () -> Unit, onNeverShowAgain: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "How to add a book into favorites",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = "You can add the book to your favorites by performing a left swipe on the Search screen.",
                fontSize = 16.sp
            )
        },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onNeverShowAgain()
                    }
                ) {
                    Text("Don't show again", color = Color.Red)
                }
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Okey", color = MaterialTheme.colors.primary)
                }
            }
        },
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface
    )
}
