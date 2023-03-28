package com.example.bookfinder.screens.common


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.bookfinder.model.Book


@Composable
fun BookList(items: MutableList<Book>) {//todo parametre olarak liste alınacak

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        SearchWidget(onSearch = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items.size) {
                BookItem(book = items[it])
            }

        }
    }
}