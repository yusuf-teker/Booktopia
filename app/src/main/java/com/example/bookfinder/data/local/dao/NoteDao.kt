package com.example.bookfinder.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.bookfinder.data.model.room.Note

@Dao
interface NoteDao {

    @Update
    suspend fun addNoteToBook(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes where bookId = :bookId")
    suspend fun getNotesForBook(bookId: String): List<Note>
}