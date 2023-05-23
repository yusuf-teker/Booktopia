package com.example.bookfinder.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookfinder.data.local.dao.BookDao
import com.example.bookfinder.data.local.dao.NoteDao
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.model.room.Note

@Database(entities = [FavoriteBook::class, Note::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}