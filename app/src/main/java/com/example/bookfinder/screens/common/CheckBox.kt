package com.example.bookfinder.screens.common

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun CustomCheckBox(name: String, onCheckChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(4.dp), spotColor = if (isChecked) Color.Green else Color.Transparent)
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)

        )
        Checkbox(checked = isChecked, onCheckedChange = {
            onCheckChange(it)
            isChecked = !isChecked
        })
    }
}


@Preview(showBackground = true)
@Composable
fun FavoriteDetailsPreview() {
    BookFinderTheme {
        CustomCheckBox(name = "Okudum"){
            Log.d("yusuf",it.toString())
        }
    }
}