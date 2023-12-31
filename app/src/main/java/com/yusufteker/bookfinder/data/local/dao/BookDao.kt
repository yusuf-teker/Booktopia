package com.yusufteker.bookfinder.data.local.dao

import androidx.room.*
import com.yusufteker.bookfinder.data.model.room.FavoriteBook

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<FavoriteBook>

    @Query("SELECT * FROM books WHERE bookId = :bookId")
    suspend fun getBookById(bookId: String): FavoriteBook?

    @Update
    suspend fun updateBook(bookEntity: FavoriteBook)

    @Delete
    suspend fun deleteFavoriteBook(bookEntity: FavoriteBook)

    @Query("DELETE FROM books WHERE bookId = :bookId")
    suspend fun deleteFavoriteBookById(bookId: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteBook(book: FavoriteBook)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<FavoriteBook>)

    @Query("SELECT isFavorite FROM books WHERE bookId = :bookId" )
    suspend fun getFavoriteStatus(bookId: String): Boolean?

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%'")
    suspend fun findFavotireBooks(query: String): List<FavoriteBook>?

    @Query("SELECT * FROM books WHERE readingStatus = :readingStatus")
    suspend fun findBooksByReadingStatus(readingStatus: Int): List<FavoriteBook>?

}
