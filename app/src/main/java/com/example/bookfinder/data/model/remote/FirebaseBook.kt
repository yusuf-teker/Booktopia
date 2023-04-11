package com.example.bookfinder.data.model.remote

data class FirebaseBook(
    val bookId : String,
    var readCount: Int,
    val favoriteCount: Int
)
