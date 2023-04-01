package com.example.bookfinder.screens.details

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.example.bookfinder.R
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.SearchInfo
import com.example.bookfinder.screens.common.FavoriteIcon
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.util.harryPotter

@Composable
fun BookDetailsScreen(book: Book) {
    val context = LocalContext.current

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
                            text = book.volumeInfo?.title
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
                            text = book.volumeInfo?.authors?.get(0) ?: stringResource(id = R.string.book_author_not_found),
                        )
                    }

                }

            }

        }

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
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${book.volumeInfo?.description}",
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.Gray
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
    BookFinderTheme {
        BookDetailsScreen(
            book = Book(
                "2",
                "kind2",
                SearchInfo("searchInfo2"),
                null,
                harryPotter,
                true
            )
        )
    }
}