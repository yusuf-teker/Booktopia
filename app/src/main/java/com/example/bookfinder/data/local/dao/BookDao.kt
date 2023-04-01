package com.example.bookfinder.data.local.dao

import androidx.room.*
import com.example.bookfinder.data.model.room.FavoriteBookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookEntity: FavoriteBookEntity)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<FavoriteBookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): FavoriteBookEntity?

    @Update
    suspend fun updateBook(bookEntity: FavoriteBookEntity)

    @Delete
    suspend fun deleteBook(bookEntity: FavoriteBookEntity)
}