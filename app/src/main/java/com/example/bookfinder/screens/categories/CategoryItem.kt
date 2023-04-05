package com.example.bookfinder.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookfinder.R
import com.example.bookfinder.data.model.remote.Category
import com.example.bookfinder.util.categoryColors

@Composable
fun CategoryItem(category: Category, onItemClicked: (categoryName: String) -> Unit) {
    val backgroundColors = mutableListOf(category.categoryColor)
    val secondColor = if (category.categoryNo== category.totalCategorySize-1) categoryColors[0] else categoryColors[category.categoryNo+1]
    backgroundColors.add(secondColor)
    val brush = Brush.linearGradient(backgroundColors)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(96.dp)
            .clickable {
                onItemClicked(category.categoryName)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(brush)){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = category.categoryImage),
                    contentDescription = category.categoryName,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category.categoryName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.white_gray),
                )
            }
        }

    }
}