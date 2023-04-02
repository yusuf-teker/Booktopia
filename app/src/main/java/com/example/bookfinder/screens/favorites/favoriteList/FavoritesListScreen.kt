package com.example.bookfinder.screens.favorites.favoriteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.bookfinder.data.model.room.FavoriteBook

@Composable
fun FavoritesListScreen(
    viewModel: FavoritesScreenViewModel,
    onItemClicked : (FavoriteBook) -> Unit
){
    val favoriteBooks = viewModel.books.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(Color.LightGray),
        ) {
            items(favoriteBooks.value.size) {
                FavoriteBookItem(book = favoriteBooks.value[it], onItemClicked, viewModel)
            }

        }
    }
}