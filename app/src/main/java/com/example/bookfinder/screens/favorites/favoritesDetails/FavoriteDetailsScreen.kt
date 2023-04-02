package com.example.bookfinder.screens.favoriteDetails

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.example.bookfinder.R
import com.example.bookfinder.screens.common.CustomCheckBox
import com.example.bookfinder.screens.common.FavoriteIcon
import com.example.bookfinder.screens.favorites.favoritesDetails.FavoriteDetailsViewModel

@Composable
fun FavoriteDetailsScreen(bookId: String, viewModel: FavoriteDetailsViewModel) {
    Log.d("yusuf",bookId)
    FavoriteDetailItem(bookId,viewModel)
}

@Composable
fun FavoriteDetailItem(bookId: String, viewModel: FavoriteDetailsViewModel){
    val context = LocalContext.current
    viewModel.getFavoriteBookById(bookId)
    val book = viewModel.book.collectAsState().value
    var noteText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(
                            if (book?.thumbnail.isNullOrEmpty())
                                R.drawable.placeholder
                            else
                                book?.thumbnail
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
                    .fillMaxHeight(0.9f)
                    .blur(7.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .weight(1f)
                            .width(48.dp)
                            .height(48.dp)
                            .padding(start = 4.dp)
                            .clickable {
                                // todo go back
                            },
                        tint = Color.Black
                    )
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(
                                    if (book?.thumbnail.isNullOrEmpty())
                                        R.drawable.placeholder
                                    else
                                        book?.thumbnail
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
                    FavoriteIcon(
                        modifier = Modifier
                            .weight(1f)
                            .width(48.dp)
                            .height(48.dp)
                            .padding(end = 4.dp),
                        onClick = { isFavorite ->
                            Toast.makeText(context, "$isFavorite", Toast.LENGTH_SHORT).show()
                            if (isFavorite){
                                viewModel.saveFavoriteBook(book = book)
                            }else{
                                viewModel.deleteFavoriteBook(book = book)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight()
                        .shadow(
                            4.dp,
                            RoundedCornerShape(16.dp),
                            spotColor = Color.DarkGray
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = book?.title
                                ?: stringResource(id = R.string.book_title_not_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2
                        )
                        Text(
                            text = book?.authors?.get(0) ?: stringResource(id = R.string.book_author_not_found),
                        )
                    }

                }

            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomCheckBox(name = "Okuyacağım", onCheckChange = {

            })
            CustomCheckBox(name = "Okudum", onCheckChange = {

            })
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Row() {
                Text(
                    text = "Notlarım", fontFamily = FontFamily.Cursive,
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
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )),
                exit = slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutLinearInEasing
                    )
                ) + fadeOut(animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                ))
            ) {
                TextField(
                    value = noteText,
                    onValueChange = {
                        noteText = it
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
                        .fillMaxWidth()
                        .height(240.dp)
                        .padding(vertical = 8.dp)
                        .border(4.dp, Color.Black),

                    )
            }


        }

    }
}
