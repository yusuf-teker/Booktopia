package com.example.bookfinder.data.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0 ,
    val bookId: String,
    val noteText: String,
    val noteColor: Long = 0xFFFF00FF,
    val noteTime: String,

)
