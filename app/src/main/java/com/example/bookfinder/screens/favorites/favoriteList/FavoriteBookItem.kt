package com.example.bookfinder.screens.favorites.favoriteList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bookfinder.data.model.room.FavoriteBook
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
                Icon(Icons.TwoTone.Favorite, contentDescription = null,Modifier.size(48.dp))
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
        swipeThreshold = 150.dp
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable{
                    if (isFavorite){
                        onItemClicked(book)
                    }
                }
                .background(
                    color = Color.LightGray
                )
                .padding(8.dp)
                .shadow(
                    4.dp,
                    RoundedCornerShape(8.dp),
                    spotColor = if (isFavorite) Color.Red else Color.DarkGray
                )
            ,

            ) {

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White
                    ),
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
                    )
                    Text(
                        text = book.description
                            ?: stringResource(id = R.string.book_description_not_found),
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    )
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .border(4.dp, Color.Black)
                )
            }

        }
    }
}
