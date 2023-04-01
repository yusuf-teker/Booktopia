package com.example.bookfinder.screens.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.screens.common.BookItem

@Composable
fun FavoritesScreen(navController: NavController) {
    val favoriteBooks: List<Book> = listOf()

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(favoriteBooks.size) {
                BookItem(book = favoriteBooks[it])
            }

        }
    }
}
