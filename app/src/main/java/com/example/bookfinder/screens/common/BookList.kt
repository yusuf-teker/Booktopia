package com.example.bookfinder.screens.common


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel


@Composable
fun BookList(items: List<Book>,viewModel: SearchScreenViewModel,  onBookClicked: (String )-> Unit) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items.size) {
            BookItem(book = items[it], viewModel, onBookClicked)
        }

    }
}