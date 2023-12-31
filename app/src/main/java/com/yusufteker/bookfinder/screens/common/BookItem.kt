package com.yusufteker.bookfinder.screens.common


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yusufteker.bookfinder.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.data.model.remote.toFavoriteBook
import com.yusufteker.bookfinder.screens.search.searchList.SearchScreenViewModel
import com.yusufteker.bookfinder.util.shadow
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun BookItem(book: Book, viewModel: SearchScreenViewModel,  onBookClicked: (String )-> Unit) {

    val context = LocalContext.current

    var isFavorite by remember {
        mutableStateOf(book.isFavorite)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(
                if (book.volumeInfo?.imageLinks?.thumbnail.isNullOrEmpty())
                   R.drawable.no_image
                else
                    book.volumeInfo?.imageLinks?.thumbnail
            )
            .crossfade(true)
            .placeholder(
                R.drawable.placeholder
            )
            .build()
    )
    val favorite = SwipeAction(

        icon = {
            Box(
                Modifier.size(96.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.TwoTone.Favorite, contentDescription = null,Modifier.size(48.dp))
            }
        },
        background = Color.Red,
        onSwipe = {
            isFavorite = !isFavorite
            if (isFavorite){
                viewModel.insertFavoriteBook(book.toFavoriteBook())
            }else{
                viewModel.deleteFavoriteBook(book.toFavoriteBook())
            }

        },

    )
    SwipeableActionsBox(
        endActions = listOf(favorite),
        swipeThreshold = 150.dp
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    onBookClicked(book.id)
                }
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Transparent,
                )
                .padding(8.dp)
                .shadow(
                    color = if (isFavorite) Color.Red else Color.Black
                )
            ,

            ) {
            Surface(modifier = Modifier
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {

                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Text(
                            text = book.volumeInfo?.title
                                ?: stringResource(id = R.string.book_title_not_found),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.fillMaxWidth(0.6f),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            color = colorResource(R.color.onSurface)
                        )
                        Text(
                            text = book.volumeInfo?.description
                                ?: stringResource(id = R.string.book_description_not_found),
                            style = MaterialTheme.typography.body2,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 3,
                            modifier = Modifier.fillMaxWidth(0.6f),
                            color = colorResource(R.color.onSurface)
                        )
                    }


                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                    )
                }
            }

        }
    }
}

