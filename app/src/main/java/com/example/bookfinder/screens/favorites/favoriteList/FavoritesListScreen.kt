package com.example.bookfinder.screens.favorites.favoriteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.screens.common.InfoPopup

@Composable
fun FavoritesListScreen(
    viewModel: FavoritesScreenViewModel,
    onItemClicked : (FavoriteBook) -> Unit
){
    val shouldShowInfoPopUp = viewModel.shouldShowInfoPopUp.collectAsState()
    val favoriteBooks = viewModel.books.collectAsState()

    if (shouldShowInfoPopUp.value){
        InfoPopup(
            onDismiss = { viewModel.hidePopUpTemporary() },
            onNeverShowAgain = { viewModel.neverShowInfoPopUpAgain() }
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ) {
        items(favoriteBooks.value.size) {
            FavoriteBookItem(book = favoriteBooks.value[it], onItemClicked, viewModel)
        }

    }
}