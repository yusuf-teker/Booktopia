package com.example.bookfinder.screens.favorites.favoriteList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.example.bookfinder.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.util.shadow
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FavoriteBookItem(book: FavoriteBook,  onItemClicked : (FavoriteBook) -> Unit, viewModel: FavoritesScreenViewModel) {

    val context = LocalContext.current

    var isFavorite by remember {
        mutableStateOf(book.isFavorite)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(
                if (book.thumbnail.isNullOrEmpty())
                    R.drawable.no_image
                else
                    book.thumbnail
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
                Icon(Icons.TwoTone.Favorite, contentDescription = null, Modifier.size(48.dp))
            }
        },
        background = Color.Red,
        onSwipe = {
            isFavorite = !isFavorite
            if (isFavorite){
                viewModel.insertFavoriteBook(book)
            }else{
                viewModel.deleteFavoriteBook(book)
            }

        },

        )
    SwipeableActionsBox(
        endActions = listOf(favorite),
        swipeThreshold = 150.dp,
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    if (isFavorite) {
                        onItemClicked(book)
                    }
                }
                .background(
                    color = Color.Transparent,
                )
                .padding(8.dp)
                .shadow(
                    color = if (isFavorite) Color.Red else Color.Black
                )

            ) {

            Row(
                Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(8.dp)
                    )   .padding(horizontal = 8.dp),
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
                        text = book.title ?: stringResource(id = R.string.book_title_not_found),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = colorResource(R.color.onSurface)
                    )
                    Text(
                        text = book.description
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
                        .padding(24.dp)
                )
            }

        }
    }
}
