package com.yusufteker.bookfinder.data.model.remote

import com.yusufteker.bookfinder.data.model.room.FavoriteBook
import com.yusufteker.bookfinder.util.getCurrentDateAsString

data class Book(
    val id: String,
    val kind: String,
    val searchInfo: SearchInfo,
    val selfLink: String?,
    val volumeInfo: VolumeInfo?,
    var isFavorite: Boolean = false
)

fun Book.toFavoriteBook(): FavoriteBook {
    return FavoriteBook(
        bookId = id,
        kind = kind,
        authors = volumeInfo?.authors ?: listOf(),
        categories = volumeInfo?.categories ?: listOf(),
        description = volumeInfo?.description ?: "",
        thumbnail = volumeInfo?.imageLinks?.thumbnail ?: "",
        language = volumeInfo?.language ?: "",
        pageCount = volumeInfo?.pageCount ?: 0,
        publishedDate = volumeInfo?.publishedDate ?: "",
        publisher = volumeInfo?.publisher ?: "",
        title = volumeInfo?.title ?: "",
        isFavorite = true,
        readingStatus = 0,
        addedDate = getCurrentDateAsString()
    )
}