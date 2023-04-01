package com.example.bookfinder.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookfinder.screens.common.BookList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookfinder.screens.common.SearchWidget

@Composable
fun SearchScreen(){
    val viewModel: SearchScreenViewModel = viewModel()
    val booksState by viewModel.books.collectAsState()
    val query = viewModel.query.collectAsState()
    Scaffold(
        topBar = {
            SearchWidget(query.value, onQueryChanged = { viewModel.setQuery(it) })
        },
        content = {
            it

            if (booksState.isNotEmpty()) {
                BookList(items = booksState)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Text(text = "No results found.")
                }
            }

        }

    )

}