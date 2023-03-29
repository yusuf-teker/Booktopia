package com.example.bookfinder.screens.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.bookfinder.model.Book
import com.example.bookfinder.screens.common.BookItem
import com.example.bookfinder.screens.common.SearchWidget

@Composable
fun FavoritesScreen() {
    val favoriteBooks: List<Book> = listOf()

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        SearchWidget(onSearch = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(favoriteBooks.size) {
                BookItem(book = favoriteBooks[it])
            }

        }
    }
}
