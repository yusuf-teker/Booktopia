package com.yusufteker.bookfinder.screens.common


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.screens.search.searchList.SearchScreenViewModel
import com.yusufteker.bookfinder.ui.theme.Dimen.bottomNavigationHeight


@Composable
fun BookList(
    books: LazyPagingItems<Book>,
    viewModel: SearchScreenViewModel,
    onBookClicked: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,

                )
    ) {

        when (books.loadState.refresh) {
            LoadState.Loading -> {
                Box(modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()) {
                    CircularProgressIndicator(
                        color = Color.Green,
                        backgroundColor = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            is LoadState.Error -> {
                Box(modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()) {
                    Text(text = "There is an error please try again later.")
                }
            }
            else -> {
                LazyColumn() {

                    items(books.itemCount){
                        BookItem(book = books.itemSnapshotList.items[it], viewModel = viewModel, onBookClicked = onBookClicked)
                    }
                    item {
                        Spacer(modifier = Modifier.height(bottomNavigationHeight))
                    }

                }
            }
        }

    }

}
