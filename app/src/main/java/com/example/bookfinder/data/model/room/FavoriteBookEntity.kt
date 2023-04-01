package com.example.bookfinder.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.VolumeInfo

@Entity(tableName = "books")
data class FavoriteBookEntity(
    @PrimaryKey
    val id: String,
    val kind: String,
    val volumeInfo: VolumeInfo?,
    var isFavorite: Boolean = false,
    var myNotes: String?,
    var isReaded: Boolean = false
) {
    constructor(book: Book, myNotes: String?, isReaded: Boolean) : this(
        book.id,
        book.kind,
        book.volumeInfo,
        book.isFavorite,
        myNotes,
        isReaded
    )
}