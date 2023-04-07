package com.example.bookfinder.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.screens.categories.CategoriesScreen
import com.example.bookfinder.screens.common.BookList
import com.example.bookfinder.screens.common.SearchWidget
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchListScreen(viewModel: SearchScreenViewModel, onBookClicked: (String) -> Unit) {
    val booksState by viewModel.books.collectAsState()
    val query = viewModel.query.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    Scaffold(
        topBar = {
            SearchWidget(onQueryChanged = { viewModel.setQueryAndSearch(it) })
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)

            ) {
                if (isLoading.value) {
                    Box(modifier = Modifier.background(Color.Transparent).fillMaxSize()) {
                        CircularProgressIndicator(
                            color = Color.Green,
                            backgroundColor = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }else{
                    if (query.value.isNullOrEmpty()) {
                        CategoriesScreen(viewModel)
                    } else {
                        if (!booksState.isNullOrEmpty()) {
                            BookList(items = booksState, viewModel, onBookClicked)
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(16.dp)

                            ) {
                                Text(text = stringResource(id = R.string.no_result_founds))
                            }
                        }
                    }
                }



            }


        }

    )

}