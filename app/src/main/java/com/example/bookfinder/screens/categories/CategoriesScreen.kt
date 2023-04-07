package com.example.bookfinder.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookfinder.R
import com.example.bookfinder.screens.search.searchList.SearchScreenViewModel
import com.example.bookfinder.ui.theme.Dimen
import com.example.bookfinder.util.createCategory


@Composable
fun CategoriesScreen(viewModel: SearchScreenViewModel) {
    val categoryNames = stringArrayResource(id = R.array.categories).toList()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = Dimen.bottomNavigationHeight)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),

        ) {

            item(
                span = { GridItemSpan(2) },
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)) {
                    Text(
                        text = stringResource(R.string.title_categories),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Center),
                        color = MaterialTheme.colors.onSurface
                    )
                }

            }

            items(categoryNames.size) {
                CategoryItem(
                    category = createCategory(it, categoryNames[it], categoryNames.size),
                    onItemClicked = {
                        viewModel.setQueryAndSearch(it)
                    }
                )
            }
        }
    }
}