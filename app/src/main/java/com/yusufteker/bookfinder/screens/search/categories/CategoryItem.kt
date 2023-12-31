package com.yusufteker.bookfinder.screens.search.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusufteker.bookfinder.R
import com.yusufteker.bookfinder.data.model.remote.Category
import com.yusufteker.bookfinder.screens.common.ResponsiveText
import com.yusufteker.bookfinder.util.categoryColors

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
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = category.categoryImage),
                    contentDescription = category.categoryName,
                    modifier = Modifier.size(48.dp).padding(end = 8.dp)
                )
                ResponsiveText(
                    modifier = Modifier.weight(1f),
                    text = category.categoryName,
                    textStyle = TextStyle(fontSize = 16 .sp)
                    //textAlign Center default
                    , color = colorResource(id = R.color.white_gray),
                    maxLines = 3
                )

            }
        }

    }
}
