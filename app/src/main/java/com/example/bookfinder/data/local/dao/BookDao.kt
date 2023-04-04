package com.example.bookfinder.data.local.dao

import androidx.room.*
import com.example.bookfinder.data.model.room.FavoriteBook

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<FavoriteBook>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): FavoriteBook?

    @Update
    suspend fun updateBook(bookEntity: FavoriteBook)

    @Delete
    suspend fun deleteFavoriteBook(bookEntity: FavoriteBook)

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteFavoriteBookById(bookId: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteBook(book: FavoriteBook)

    @Query("SELECT isFavorite FROM books WHERE id = :bookId" )
    suspend fun getFavoriteStatus(bookId: String): Boolean?
}
