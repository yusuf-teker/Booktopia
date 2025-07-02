package com.yusufteker.bookfinder.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusufteker.bookfinder.R
import com.yusufteker.bookfinder.screens.favorites.favoriteList.FavoritesScreenViewModel
import com.yusufteker.bookfinder.screens.favorites.favoriteList.SearchAppBarState

const val FILTER_TO_BE_READ = 0
const val FILTER_READINGS = 1
const val FILTER_READS = 2
const val SORTED_BY_DATE = 3
const val SHOW_ALL = 4

@Composable
fun CustomTopAppBar(
    viewModel: FavoritesScreenViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultTopAppBar(
                onSearchClicked = {
                    viewModel.setSearchAppBarState(SearchAppBarState.OPENED)
                },
                onFilterClicked = {
                    when(it){
                        SORTED_BY_DATE -> null
                        FILTER_TO_BE_READ, FILTER_READINGS, FILTER_READS  -> viewModel.findBooksByReadingStatus(it)
                        SHOW_ALL ->  viewModel.getAllFavoriteBooks()
                        else -> null
                    }

                },
                onSyncFavoriteBooks = {
                    viewModel.syncFavoriteBooks()
                }
            )

        }
        else -> {
            SearchTopAppBar(
                text = searchTextState,
                onTextChange = { text ->
                    viewModel.setSearchTextState(text)
                },
                onCloseClicked = {
                    viewModel.setSearchAppBarState(SearchAppBarState.CLOSED)
                    viewModel.setSearchTextState("")
                    viewModel.getAllFavoriteBooks()
                },
                onSearchClicked = {
                    viewModel.findFavoriteBooks(it)
                }
            )
        }
    }
}

@Composable
fun DefaultTopAppBar(
    onSearchClicked: () -> Unit,
    onFilterClicked: (Int) -> Unit,
    onSyncFavoriteBooks: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .statusBarsPadding()


    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            title = {
                Text(
                    text = stringResource(id = R.string.my_favorites),
                    color = MaterialTheme.colors.onSurface
                )
            },
            actions = {
                AppBarActions(
                    onSearchClicked = onSearchClicked,
                    onFilterClicked = onFilterClicked,
                    onSyncFavoriteBooks = onSyncFavoriteBooks,
                )
            }
        )
    }
}

@Composable
fun AppBarActions(
    onSearchClicked: () -> Unit,
    onFilterClicked: (Int) -> Unit,
    onSyncFavoriteBooks: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    FilterAction(onFilterClicked)
    MoreAction(onSyncFavoriteBooks)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onSearchClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search_icon",
            tint = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun FilterAction(onFilter: (Int) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = !expanded
        }
    ) {
        Image(

            modifier =Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(R.string.filter),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }

        ) {

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilter(SORTED_BY_DATE)
                }
            ) {
                Text(
                    text = stringResource(R.string.sort_by_date),
                    color = MaterialTheme.colors.onSurface
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilter(FILTER_TO_BE_READ)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.to_be_read),
                    color = MaterialTheme.colors.onSurface
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilter(FILTER_READINGS)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.readings),
                    color = MaterialTheme.colors.onSurface
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilter(FILTER_READS)
                }
            ) {
                Text(
                    text = stringResource(R.string.reads),
                    color = MaterialTheme.colors.onSurface
                )
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilter(SHOW_ALL)
                }
            ) {
                Text(
                    text = stringResource(R.string.show_all),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }


    }
}

@Composable
fun MoreAction(
    onSyncFavoriteBooks: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colors.onSurface
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSyncFavoriteBooks()
                }
            ) {
                Text(
                    text = stringResource(R.string.sync_favorite_books),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}


@Preview
@Composable
fun SearchAppBarPreview() {
    SearchTopAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = { },
        onSearchClicked = {}
    )
}

@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .statusBarsPadding()

    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Transparent,
            elevation = 0.dp
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.search),
                        color = MaterialTheme.colors.onSurface
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(
                                id = R.string.search
                            ),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onCloseClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(
                                id = R.string.clear
                            ),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                )
            )
        }
    }
}
