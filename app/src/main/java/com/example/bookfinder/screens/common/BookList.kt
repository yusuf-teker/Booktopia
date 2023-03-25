package com.example.bookfinder.screens.common


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookfinder.model.Book


@Composable
fun BookList(items: MutableList<Book>) {//todo parametre olarak liste alınacak


    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        //verticalArrangement = Arrangement.Center
    ) {
        items(items.size) {
            BookItem(book = items[it])
        }

    }
}