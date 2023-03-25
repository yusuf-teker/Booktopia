package com.example.bookfinder.model

data class Book(
    val etag: String,
    val id: String,
    val kind: String,
    val searchInfo: SearchInfo,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
    var isFavorite: Boolean = false
)