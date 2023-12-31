package com.yusufteker.bookfinder.data.local.dao

import androidx.room.*
import com.yusufteker.bookfinder.data.model.room.Note

@Dao
interface NoteDao {

    @Upsert(entity = Note::class)
    suspend fun addNoteToBook(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes where bookId = :bookId")
    suspend fun getNotesForBook(bookId: String): List<Note>
}