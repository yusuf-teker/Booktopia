package com.yusufteker.bookfinder.screens.search

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import com.yusufteker.bookfinder.R
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.yusufteker.bookfinder.screens.search.categories.CategoriesScreen
import com.yusufteker.bookfinder.screens.common.BookList
import com.yusufteker.bookfinder.screens.common.SearchWidget
import com.yusufteker.bookfinder.screens.search.searchList.SearchScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchListScreen(viewModel: SearchScreenViewModel, onBookClicked: (String) -> Unit) {
    val query = viewModel.query.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    val books = viewModel.bookPager.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                SearchWidget(onQueryChanged = {
                    viewModel.setQueryAndSearch(it)
                })
            }

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
                        if (books.itemCount!=0) {
                            BookList(books = books, viewModel, onBookClicked)
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
    BackHandler {

       viewModel.clearQuery()
    }

}