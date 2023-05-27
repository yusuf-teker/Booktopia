package com.example.bookfinder.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookfinder.util.categoryColorsLongValue

@Composable
fun MyColorSelector(
    onColorSelected: (index: Int) -> Unit
){
    val selected = remember {
        mutableStateOf(0)
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(72.dp),
        Modifier
            .padding(32.dp)
            .background(color = Color.DarkGray, RoundedCornerShape(32.dp))
    ){

        items(categoryColorsLongValue.size) { index ->


            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        selected.value = index
                        onColorSelected(selected.value)
                    }

            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(categoryColorsLongValue[index]),
                            shape = CircleShape
                        )
                        .size(48.dp)
                ) {
                    if (selected.value == index) {
                        Icon(
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }


            }

        }
    }
}