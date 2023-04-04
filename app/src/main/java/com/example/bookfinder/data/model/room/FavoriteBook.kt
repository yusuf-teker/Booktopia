package com.example.bookfinder.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Entity(tableName = "books")
data class FavoriteBook(
    @PrimaryKey
    val id: String,
    val kind: String,
    val authors: List<String> = listOf(),
    val categories: List<String> = listOf(),
    val description: String = "",
    val thumbnail: String = "",
    val language: String = "",
    val pageCount: Int = 0,
    val publishedDate: String = "",
    val publisher: String = "",
    val title: String = "",
    var isFavorite: Boolean = false,
    var myNotes: String?,
    var readingStatus: Int = -1
)

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return value.joinToString(",")
    }
}