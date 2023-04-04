package com.example.bookfinder.screens.common

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import com.example.bookfinder.ui.theme.BookFinderTheme

@Composable
fun FavoriteIcon(isFavorite : Boolean, onClick: (Boolean) -> Unit, modifier: Modifier) {
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val contentDescription = if (isFavorite) "Favorited" else "Not favorited"
    Icon(
        icon,
        contentDescription = contentDescription,
        tint = if (isFavorite) Color.Red else Color.White,
        modifier = modifier.clickable(
            onClick = {
                onClick(!isFavorite)
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteIconPreview() {
    BookFinderTheme {
        val context = LocalContext.current
        FavoriteIcon(
            isFavorite = true,
            onClick = {isFavorite ->
                Toast.makeText(context,"$isFavorite",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
        )
    }
}