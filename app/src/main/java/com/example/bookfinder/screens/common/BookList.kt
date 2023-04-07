package com.example.bookfinder.screens.common


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel


@Composable
fun BookList(items: List<Book>, viewModel: SearchScreenViewModel, onBookClicked: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                shape = RoundedCornerShape(8.dp),
                color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,

                )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),

            ) {

            items(items.size, key = {index ->
                items[index].id
            }) {
                BookItem(book = items[it], viewModel, onBookClicked)
            }
        }
    }

}