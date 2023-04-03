package com.example.bookfinder.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookfinder.screens.common.BookList
import com.example.bookfinder.screens.categories.CategoriesScreen
import com.example.bookfinder.screens.common.SearchWidget
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel
import com.example.bookfinder.ui.theme.Dimens.Dimens.bottomNavigationHeight

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchListScreen(viewModel: SearchScreenViewModel,  onBookClicked: (String )-> Unit ){
    val booksState by viewModel.books.collectAsState()
    val query = viewModel.query.collectAsState()
    Scaffold(
        topBar = {
            SearchWidget(onQueryChanged = { viewModel.setQuery(it) })
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomNavigationHeight)){
                    if (query.value.isNullOrEmpty()){
                    CategoriesScreen(viewModel)
                }else{
                        if (!booksState.isNullOrEmpty()) {
                            BookList(items = booksState, viewModel, onBookClicked)
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

            }


        }

    )

}