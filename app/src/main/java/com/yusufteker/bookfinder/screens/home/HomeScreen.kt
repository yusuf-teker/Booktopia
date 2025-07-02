package com.yusufteker.bookfinder.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.yusufteker.bookfinder.R
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.screens.app.Screen
import com.yusufteker.bookfinder.screens.common.AutoScrollingLazyRow
import com.yusufteker.bookfinder.screens.common.ResponsiveText
import com.yusufteker.bookfinder.screens.search.searchDetail.SearchDetailScreen
import com.yusufteker.bookfinder.screens.search.searchDetail.SearchDetailViewModel
import com.yusufteker.bookfinder.ui.theme.BookFinderTheme
import com.yusufteker.bookfinder.ui.theme.Dimen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreenContent(
                homeScreenViewModel,
                onLogout = {
                    onLogout()
                },
                onBookClicked = {
                    navController.navigate("${Screen.SearchDetails.route}/$it")
                }
            )
        }
        composable(
            route = "${Screen.SearchDetails.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments!!.getString("bookId") ?: ""
            val searchDetailViewModel = hiltViewModel<SearchDetailViewModel>()
            SearchDetailScreen(
                bookId = bookId, viewModel = searchDetailViewModel,
                navController = navController
            )
        }
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    viewModel: HomeScreenViewModel,
    onLogout: () -> Unit,
    onBookClicked: (String) -> Unit
) {
    val mostReadedBooks = viewModel.mostReadedBooks.collectAsState()
    val mostFavoriteBooks = viewModel.mostFavoriteBooks.collectAsState()
    val newestBooks = viewModel.newestBookPager.collectAsLazyPagingItems()


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .statusBarsPadding()
                .background(MaterialTheme.colors.surface)
            ) {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colors.onSurface
                        )
                    },
                    actions = {
                        MoreAction(
                            onLogoutClicked = {
                                onLogout()
                            }
                        )
                    }

                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = Dimen.bottomNavigationHeight)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,

            ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp)
                    .align(
                        Alignment.End
                    )
            ) {
                FavoriteBooksRow(mostReadedBooks.value, stringResource(R.string.most_readed), onBookClicked = onBookClicked, "Henüz kimse kitap okumadı  :(")
            }

            Box(
                modifier = Modifier
                    //.fillMaxWidth()
                    .weight(1f)
                    .height(200.dp)
                    //.border(2.dp, Color.Red)
                    .align(
                        Alignment.End
                    )
            ) {
                FavoriteBooksRow(mostFavoriteBooks.value, stringResource(R.string.most_favorited), onBookClicked = onBookClicked, "Henüz kimse kitap beğenmedi  :(")
            }



            Box(
                modifier = Modifier
                    //.fillMaxWidth()
                    .weight(1f)
                    .height(200.dp)
                    //.border(2.dp, Color.Red)
                    .align(
                        Alignment.End
                    )
            ) {
                BooksRow(newestBooks,
                    stringResource(R.string.newest),
                    onBookClicked = {
                        onBookClicked(it)
                    }
                )
            }
        }
    }
}


@Composable
fun FavoriteBooksRow(
    favoriteBooks: List<Book>?,
    header: String,
    onBookClicked: (String) -> Unit,
    emptyListMessage: String = "Empty"
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        //.border(3.dp, Color.DarkGray)
        //.fillMaxHeight(0.7f)
    ) {
        // HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface)
            )
            ResponsiveText(
                text = header, textStyle = MaterialTheme.typography.h6, modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface)
            )
        }

        if (!favoriteBooks.isNullOrEmpty()) {

            AutoScrollingLazyRow(favoriteBooks){
                val imageWidth = 150.dp
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .padding(end = 16.dp)
                    //.border(2.dp, color = Color.Magenta),
                    ,
                    horizontalAlignment = Alignment.Start
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(
                                    it.volumeInfo?.imageLinks?.thumbnail?.ifEmpty { R.drawable.placeholder }
                                )
                                .crossfade(true)
                                .placeholder(R.drawable.placeholder)
                                .transformations(RoundedCornersTransformation(1f))
                                .size(Size.ORIGINAL)
                                .build()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .aspectRatio(0.7f)
                            .width(imageWidth)
                            //.border(2.dp, Color.Red)
                            .align(Alignment.Start)
                            .clickable {
                                onBookClicked(it.id)
                            }
                        //.wrapContentWidth()
                    )

                    Text(
                        text = it.volumeInfo?.categories?.firstOrNull()?: stringResource(R.string.no_category),
                        color = Color.Green,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(start = 2.dp)
                    )
                    Text(
                        text = it.volumeInfo?.title?: stringResource(R.string.no_title),
                        color = MaterialTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(start = 2.dp)
                    )
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()
            ) {

                Text(
                    text = emptyListMessage,
                    modifier = Modifier.align(Alignment.Center),
                    style = androidx.compose.ui.text.TextStyle.Default,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }

    }

}

@Composable
fun BooksRow(books: LazyPagingItems<Book>, header: String, onBookClicked: (String) -> Unit) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

    ) {

        // HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface)
            )
            ResponsiveText(
                text = header, textStyle = MaterialTheme.typography.h6, modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface)
            )
        }


        if (books.itemCount!=0) {

            AutoScrollingLazyRow(books.itemSnapshotList.items.distinctBy { it.id }){
                val imageWidth = 150.dp
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .padding(end = 16.dp)
                    //.border(2.dp, color = Color.Magenta),
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(
                                    it.volumeInfo?.imageLinks?.thumbnail?.ifEmpty { R.drawable.placeholder }
                                )
                                .crossfade(true)
                                .placeholder(R.drawable.placeholder)
                                .transformations(RoundedCornersTransformation(1f))
                                .size(Size.ORIGINAL)
                                .build()
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .aspectRatio(0.7f)
                            .width(imageWidth)
                            //.border(2.dp, Color.Red)
                            .align(Alignment.Start)
                            .clickable {
                                onBookClicked(it.id)
                            }
                        //.wrapContentWidth()
                    )

                    Text(
                        text = (it.volumeInfo?.categories?.firstOrNull()
                            ?: stringResource(id = R.string.no_category)),
                        color = Color.Green,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(start = 2.dp)
                    )
                    Text(
                        text = it.volumeInfo?.title ?: stringResource(id = R.string.no_title),
                        color = MaterialTheme.colors.onSurface,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(start = 2.dp)
                    )
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Color.Green,
                    backgroundColor = MaterialTheme.colors.surface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun FavoritesSectionPreview() {
    BookFinderTheme {
        Column(modifier = Modifier.fillMaxSize()) {

            FavoriteBooksRow(favoriteBooks = arrayListOf(), header = "", onBookClicked = {})

        }

    }

}

@Composable
fun MoreAction(onLogoutClicked: () -> Unit) {
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
                    onLogoutClicked()
                }
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}
