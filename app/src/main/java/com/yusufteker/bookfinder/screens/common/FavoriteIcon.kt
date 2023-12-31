package com.yusufteker.bookfinder.screens.common

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yusufteker.bookfinder.R

import com.yusufteker.bookfinder.ui.theme.BookFinderTheme
import com.yusufteker.bookfinder.ui.theme.Dimen.circleIconPadding

@Composable
fun FavoriteIcon(isFavorite : Boolean, onClick: (Boolean) -> Unit, modifier: Modifier) {
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
    val contentDescription = if (isFavorite) stringResource(R.string.remove_from_favorites) else stringResource(
            R.string.add_to_favorites)
    Icon(
        icon,
        contentDescription = contentDescription,
        tint = if (isFavorite) Color.Red else Color.White,
        modifier = modifier.clickable(
            onClick = {
                onClick(!isFavorite)
            }
        ).padding(circleIconPadding)
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteIconPreview() {
    BookFinderTheme {
        val context = LocalContext.current
        FavoriteIcon(
            isFavorite = true,
            onClick = {isFavorite ->
                Toast.makeText(context,"$isFavorite",Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
        )
    }
}