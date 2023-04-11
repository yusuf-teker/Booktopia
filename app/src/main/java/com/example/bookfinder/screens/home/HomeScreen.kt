package com.example.bookfinder.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.example.bookfinder.R
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.screens.app.Screen
import com.example.bookfinder.screens.common.ResponsiveText
import com.example.bookfinder.screens.search.searchDetail.SearchDetailScreen
import com.example.bookfinder.screens.search.searchDetail.SearchDetailViewModel
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.ui.theme.Dimen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(onLogout: () -> Unit, viewModel: HomeScreenViewModel) {
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

@Composable
fun HomeScreenContent(
    viewModel: HomeScreenViewModel,
    onLogout: () -> Unit,
    onBookClicked: (String) -> Unit
) {
    val newestBooks = viewModel.newestBooks.collectAsState()

    var favoriteBooks: ArrayList<FavoriteBook> = arrayListOf()
    repeat(1) {
        favoriteBooks.add(
            FavoriteBook(
                "1",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=caMWEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
        favoriteBooks.add(
            FavoriteBook(
                "2",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=NmuNEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
        favoriteBooks.add(
            FavoriteBook(
                "7",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=caMWEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
        favoriteBooks.add(
            FavoriteBook(
                "8",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=caMWEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
        favoriteBooks.add(
            FavoriteBook(
                "3",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=NmuNEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
        favoriteBooks.add(
            FavoriteBook(
                "4",
                "",
                emptyList(),
                listOf("CategoryName"),
                "",
                "http://books.google.com/books/content?id=caMWEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "",
                0,
                "",
                "",
                "BookTitle",
                false,
                "",
                -1,
                "1"
            )
        )
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
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
        it
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                //.border(2.dp, Color.Green)
                .padding(bottom = Dimen.bottomNavigationHeight)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,

            ) {

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
                favoriteBooksRow(favoriteBooks, "Çok okunanlar", onBookClicked = onBookClicked)
                //mostFavoriteBooksSection(favoriteBooks)
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
                favoriteBooksRow(favoriteBooks, "Çok beğenilenler", onBookClicked = onBookClicked)
                //mostReadedBooksSection(favoriteBooks)
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
                booksRow(newestBooks.value,
                    "En yeniler",
                    onBookClicked = {
                        onBookClicked(it)
                    }
                )
            }
        }
    }
}


@Composable
fun favoriteBooksRow(
    favoriteBooks: ArrayList<FavoriteBook>,
    header: String,
    onBookClicked: (String) -> Unit
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

        if (favoriteBooks.isNotEmpty()) {
            // ROW LIST
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()

            ) {
                items(favoriteBooks.size, key = { index ->
                    favoriteBooks[index].id
                }) {

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
                                        favoriteBooks[it].thumbnail.ifEmpty { R.drawable.placeholder }
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
                                    onBookClicked(favoriteBooks[it].id ?: "")
                                }
                            //.wrapContentWidth()
                        )

                        Text(
                            text = favoriteBooks[it].categories[0] + "aasdadasd",
                            color = Color.Green,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.sp,
                            maxLines = 1,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(start = 2.dp)
                        )
                        Text(
                            text = favoriteBooks[it].title,
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

@Composable
fun booksRow(books: List<Book>, header: String, onBookClicked: (String) -> Unit) {

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


        if (books.isNotEmpty()) {
            // ROW LIST
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()

            ) {
                items(books.size, key = { index ->
                    books[index].id
                }) {

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
                                        books[it].volumeInfo?.imageLinks?.thumbnail?.ifEmpty { R.drawable.placeholder }
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
                                    onBookClicked(books[it].id ?: "")
                                }
                            //.wrapContentWidth()
                        )

                        Text(
                            text = (books[it].volumeInfo?.categories?.firstOrNull()
                                ?: "No Category"),
                            color = Color.Green,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.sp,
                            maxLines = 1,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(start = 2.dp)
                        )
                        Text(
                            text = books[it].volumeInfo?.title ?: "No Title",
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

/*
@Composable
fun mostReadedBooksSection(favoriteBooks: ArrayList<FavoriteBook>) { //todo replace it with bookId and bookImageUrl
val context = LocalContext.current

Column(modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight()
    //.fillMaxHeight(0.7f)
) {
    // HEADER
    ResponsiveText(text = "En çok okunanlar...", textStyle = MaterialTheme.typography.h6 )

    // ROW LIST
    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()

    ) {
        items(favoriteBooks.size, key = { index ->
            favoriteBooks[index].id
        }) {

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
                                favoriteBooks[it].thumbnail.ifEmpty { R.drawable.placeholder }
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
                        .fillMaxHeight(0.5f)
                        .aspectRatio(0.7f)
                        .width(imageWidth)
                        //.border(2.dp, Color.Red)
                        .align(Alignment.Start)
                        .onSizeChanged { intSize ->

                        }
                    //.wrapContentWidth()
                )

                Text(
                    text = favoriteBooks[it].categories[0]+"aasdadasd",
                    color = Color.Green,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = favoriteBooks[it].title,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
            }

        }
    }
}

}
@Composable
fun newestBooksSection(favoriteBooks: ArrayList<FavoriteBook>) { //todo replace it with bookId and bookImageUrl
val context = LocalContext.current

Column(modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight()
    //.fillMaxHeight(0.7f)
) {
    // HEADER
    ResponsiveText(text = "En yeniler...", textStyle = MaterialTheme.typography.h6 )

    // ROW LIST
    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()

    ) {
        items(favoriteBooks.size, key = { index ->
            favoriteBooks[index].id
        }) {

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
                                favoriteBooks[it].thumbnail.ifEmpty { R.drawable.placeholder }
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
                        .fillMaxHeight(0.5f)
                        .aspectRatio(0.7f)
                        .width(imageWidth)
                        //.border(2.dp, Color.Red)
                        .align(Alignment.Start)
                        .onSizeChanged { intSize ->

                        }
                    //.wrapContentWidth()
                )

                Text(
                    text = favoriteBooks[it].categories[0]+"aasdadasd",
                    color = Color.Green,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = favoriteBooks[it].title,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
            }

        }
    }
}

}
@Composable
fun mostFavoriteBooksSection(favoriteBooks: ArrayList<FavoriteBook> ) { //todo replace it with bookId and bookImageUrl
val context = LocalContext.current

Column(modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight()
    //.border(3.dp, Color.DarkGray)
    //.fillMaxHeight(0.7f)
) {
    // HEADER
    ResponsiveText(text ="En çok beğenilenler...", textStyle = MaterialTheme.typography.h6 )
    // ROW LIST
    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()

    ) {
        items(favoriteBooks.size, key = { index ->
            favoriteBooks[index].id
        }) {

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
                                favoriteBooks[it].thumbnail.ifEmpty { R.drawable.placeholder }
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
                        .fillMaxHeight(0.5f)
                        .aspectRatio(0.7f)
                        .width(imageWidth)
                        //.border(2.dp, Color.Red)
                        .align(Alignment.Start)
                        .onSizeChanged { intSize ->

                        }
                    //.wrapContentWidth()
                )

                Text(
                    text = favoriteBooks[it].categories[0]+"aasdadasd",
                    color = Color.Green,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = favoriteBooks[it].title,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp)
                )
            }

        }
    }
}

}
*/
@Preview(showBackground = true)
@Composable
fun favoritesSectionPreview() {
    BookFinderTheme {
        Column(modifier = Modifier.fillMaxSize()) {

            favoriteBooksRow(favoriteBooks = arrayListOf(), header = "", onBookClicked = {})

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