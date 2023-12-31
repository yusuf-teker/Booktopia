package com.yusufteker.bookfinder.data.model.remote

import androidx.compose.ui.graphics.Color

data class Category(
    val categoryNo: Int,
    val categoryName: String,
    val categoryImage: Int,
    val categoryColor: Color,
    val categortQuery: String,
    val totalCategorySize: Int
)
