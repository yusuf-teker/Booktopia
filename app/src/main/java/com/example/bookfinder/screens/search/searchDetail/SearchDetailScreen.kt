package com.example.bookfinder.screens.search.searchDetail

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.screens.common.FavoriteIcon

@Composable
fun SearchDetailScreen(viewModel: SearchDetailViewModel, bookId: String, navController: NavController) {

    val book = viewModel.book.collectAsState().value
    if (book != null){
        BookDetailsScreen(book = book,navController)
    }else{
        viewModel.getBookById(bookId)
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Loading", modifier = Modifier.align(Center))
        }

    }
}

@Composable
fun BookDetailsScreen(book: Book, navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(
                            if (book.volumeInfo?.imageLinks?.thumbnail.isNullOrEmpty())
                                R.drawable.placeholder
                            else
                                book.volumeInfo?.imageLinks?.thumbnail
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Black, CircleShape)
                            .size(36.dp)
                            .shadow(2.dp, CircleShape)
                            .padding(4.dp)

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(36.dp)
                                .clickable {
                                    navController.popBackStack()
                                }.align(Center),
                            tint = Color.White,

                            )
                    }

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(
                                    if (book.volumeInfo?.imageLinks?.thumbnail.isNullOrEmpty())
                                        R.drawable.placeholder
                                    else
                                        book.volumeInfo?.imageLinks?.thumbnail
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
                            .aspectRatio(1f)
                            .width(IntrinsicSize.Min)
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.Black, CircleShape)
                            .size(36.dp)
                            .shadow(2.dp, CircleShape)
                            .padding(4.dp)
                    ) {
                        FavoriteIcon(
                            modifier = Modifier
                                .size(36.dp)
                                .align(Center),
                            onClick = { isFavorite ->
                                Toast.makeText(context, "$isFavorite", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

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
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = book.volumeInfo?.title
                                ?: stringResource(id = R.string.book_title_not_found),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            color = MaterialTheme.colors.onSurface
                        )
                        if (!book.volumeInfo?.authors.isNullOrEmpty()) {
                            Text(
                                text = book.volumeInfo?.authors?.get(0)
                                    ?: stringResource(id = R.string.book_author_not_found),
                                color = MaterialTheme.colors.onSurface
                            )
                        }

                    }

                }

            }

        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            book.volumeInfo?.categories?.joinToString(separator = " ")?.let {
                Text(
                    text = it,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (!book.volumeInfo?.description.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
                    .padding(16.dp)

            ) {
                Text(
                    text = "What is this book about?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colors.onSurface
                )
                book.volumeInfo?.description?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }


    }

}