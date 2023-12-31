package com.yusufteker.bookfinder.data.model.remote

data class VolumeInfo(
    val authors: List<String> = listOf(),
    val categories: List<String> = listOf(),
    val description: String = "",
    val imageLinks: ImageLinks? = null,
    val infoLink: String = "",
    val language: String = "",
    val pageCount: Int = 0,
    val previewLink: String = "",
    val printType: String = "",
    val publishedDate: String = "",
    val publisher: String = "",
    val title: String = ""
)