package com.example.bookfinder.data.model.remote

data class FirebaseBook(
    val bookId : String = "",
    var readCount: Int = 0,
    var favoriteCount: Int = 1
)
