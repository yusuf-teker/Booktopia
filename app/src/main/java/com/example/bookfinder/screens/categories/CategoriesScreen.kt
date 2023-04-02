package com.example.bookfinder.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel
import com.example.bookfinder.util.categories


@Composable
fun CategoriesScreen(viewModel: SearchScreenViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories.size){
                CategoryItem(
                    category = categories[it],
                    onItemClicked = {
                        viewModel.setQuery(it)
                        viewModel.searchBooks()
                    }
                )
            }
        }
    }
}