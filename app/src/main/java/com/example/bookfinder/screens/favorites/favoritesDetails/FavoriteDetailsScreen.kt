package com.example.bookfinder.screens.favorites.favoritesDetails

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.example.bookfinder.R
import com.example.bookfinder.screens.common.CustomCheckboxGroup
import com.example.bookfinder.screens.common.FavoriteIcon
import com.example.bookfinder.ui.theme.Dimen.bottomNavigationHeight
import com.example.bookfinder.ui.theme.Dimen.circleIconPadding

@Composable
fun FavoriteDetailsScreen(
    bookId: String,
    viewModel: FavoriteDetailsViewModel,
    navController: NavController
) {
    Log.d("yusuf", bookId)
    FavoriteDetailItem(bookId, viewModel, navController)
}

@Composable
fun FavoriteDetailItem(
    bookId: String,
    viewModel: FavoriteDetailsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    viewModel.getFavoriteBookById(bookId)
    val book = viewModel.book.collectAsState()
    val noteText = viewModel.noteText.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val isFavorite = viewModel.isFavorite.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomNavigationHeight)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(
                                if (book.value?.thumbnail.isNullOrEmpty())
                                    R.drawable.placeholder
                                else
                                    book.value?.thumbnail
                            )
                            .crossfade(true)
                            .placeholder(
                                R.drawable.placeholder
                            ).transformations(RoundedCornersTransformation(1f))
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .blur(7.dp)
                )

                Column( //iconlar ve image
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.Black, CircleShape)
                                .size(36.dp)
                                .shadow(2.dp, CircleShape)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.back),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable {
                                        navController.popBackStack()
                                    }.padding(circleIconPadding),
                                tint = Color.White
                            )
                        }
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context)
                                    .data(
                                        if (book.value?.thumbnail.isNullOrEmpty())
                                            R.drawable.placeholder
                                        else
                                            book.value?.thumbnail
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
                                .aspectRatio(1f)
                                .width(IntrinsicSize.Min)
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.Black, CircleShape)
                                .size(36.dp)
                                .shadow(2.dp, CircleShape)

                        ) {
                            FavoriteIcon(
                                isFavorite.value,
                                modifier = Modifier
                                    .size(36.dp)
                                    .shadow(2.dp, CircleShape),
                                onClick = { isFavorite ->

                                    if (isFavorite) {
                                        viewModel.saveFavoriteBook(book = book.value)
                                    } else {
                                        viewModel.deleteFavoriteBookById(book.value?.id ?:"")
                                    }
                                    viewModel.setIsFavorite(isFavorite)
                                }
                            )

                        }
                    }

                    Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                    Box(
                        //Kitap Başlığı ve Yazarı
                        Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .shadow(
                                4.dp,
                                RoundedCornerShape(16.dp),
                                spotColor = Color.DarkGray
                            )
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 32.dp, vertical = 4.dp),
                    ) {
                        Column(
                            modifier = Modifier.wrapContentSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = book.value?.title
                                    ?: stringResource(id = R.string.book_title_not_found),
                                modifier = Modifier
                                    .wrapContentSize(),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                color = Color.Black
                            )
                            if (!book.value?.authors.isNullOrEmpty()) {
                                Text(
                                    text = book.value?.authors?.get(0)
                                        ?: stringResource(id = R.string.book_author_not_found),
                                    color = Color.Black
                                )
                            }

                        }

                    }

                }

            }
        }

        item {

            val selectedBox by viewModel.selectedBox.collectAsState()

            CustomCheckboxGroup(
                selectedBox = selectedBox,
                onBoxSelected = { index ->
                    viewModel.setSelectedBox(index)
                },
                options = listOf(stringResource(R.string.to_be_read), stringResource(R.string.reading), stringResource(
                                    R.string.read)
                                )
            )
            Column(
                //Notlarım
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {

                Row() {
                    Text(
                        text = stringResource(R.string.my_notes), fontFamily = FontFamily.Cursive,
                        fontSize = 32.sp
                    )
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.rotate(if (expanded) 180f else 0f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) + fadeIn(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutLinearInEasing
                        )
                    ) + fadeOut(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
                )  {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = noteText.value,
                            onValueChange = {
                                viewModel.setNoteText(it)
                            },
                            maxLines = 5,
                            textStyle = MaterialTheme.typography.subtitle1,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colors.onBackground,
                                backgroundColor = MaterialTheme.colors.background,
                                cursorColor = MaterialTheme.colors.onBackground,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),

                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .height(240.dp)
                                .padding(vertical = 8.dp)
                                .border(4.dp, MaterialTheme.colors.onSurface)
                        )

                    }

                }


            }
            TextButton(onClick = {
                viewModel.updateBook()
            }) {
                Text(text = stringResource(R.string.save))
            }
        }


    }
}


