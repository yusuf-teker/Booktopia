package com.example.bookfinder.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.bookfinder.data.model.room.Note

@Dao
interface NoteDao {

    @Update
    suspend fun addNoteToBook(note: Note)

    @Query("SELECT * FROM notes where bookId = :bookId")
    suspend fun getNotesForBook(bookId: String): List<Note>
}