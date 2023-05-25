package com.example.bookfinder.screens.favorites.favoriteList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.bookfinder.R
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.screens.common.CustomTopAppBar
import com.example.bookfinder.screens.common.InfoPopup
import com.example.bookfinder.ui.theme.Dimen.bottomNavigationHeight

@Composable
fun FavoritesListScreen(
    viewModel: FavoritesScreenViewModel,
    onItemClicked: (FavoriteBook) -> Unit
) {
    val shouldShowInfoPopUp = viewModel.shouldShowInfoPopUp.collectAsState()
    val favoriteBooks = viewModel.books.collectAsState()
    val searchAppBarState = viewModel.searchAppBarState.collectAsState()
    val searchTextState = viewModel.searchTextState.collectAsState()

    if (shouldShowInfoPopUp.value) {
        InfoPopup(
            onDismiss = { viewModel.hidePopUpTemporary() },
            onNeverShowAgain = { viewModel.neverShowInfoPopUpAgain() }
        )
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                viewModel,
                searchAppBarState = searchAppBarState.value,
                searchTextState.value
            )
        },
        modifier = Modifier
            .padding(bottom = bottomNavigationHeight)
            .background(
                Color.Transparent
            )
    ) {

        Box(
            modifier = Modifier
                .background(
                    if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ).padding(it)
                .fillMaxSize()

        ) {
            if (favoriteBooks.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                ) {

                    items(favoriteBooks.value.size, key = { index ->
                        favoriteBooks.value[index].bookId
                    }) {
                        FavoriteBookItem(
                            book = favoriteBooks.value[it],
                            onItemClicked, viewModel,

                        )
                    }


                }
            }else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.empty), contentDescription = "empty")
                    Text(
                        text = stringResource(id = R.string.empty_favorite_book_message),
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }

            }
        }

    }
}