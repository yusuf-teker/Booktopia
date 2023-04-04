package com.example.bookfinder.screens.favorites.favoriteList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.screens.common.InfoPopup
import com.example.bookfinder.screens.favorites.favoritesDetails.FavoriteDetailItem
import com.example.bookfinder.ui.theme.Dimens.Dimens

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar")
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) {
        it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Dimens.bottomNavigationHeight)
        ) {
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
    }


}