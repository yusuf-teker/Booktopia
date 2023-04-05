package com.example.bookfinder.data.repositories

import com.example.bookfinder.data.local.dao.BookDao
import com.example.bookfinder.data.model.room.FavoriteBook
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val bookDao: BookDao
) {
    suspend fun deleteBookFromFavorites(book: FavoriteBook) {
        bookDao.deleteFavoriteBook(book)
    }
    suspend fun insertBookToFavorites(book: FavoriteBook) {
        bookDao.insertFavoriteBook(book)
    }
    suspend fun findFavotireBooks(query: String): List<FavoriteBook>{
        return bookDao.findFavotireBooks(query) ?: emptyList()
    }
    suspend fun findBooksByReadingStatus(readingStatus: Int): List<FavoriteBook>{
        return bookDao.findBooksByReadingStatus(readingStatus) ?: emptyList()
    }

    suspend fun deleteBookFromFavoritesById(bookId: String) {
        bookDao.deleteFavoriteBookById(bookId)
    }
    suspend fun getAllFavoriteBooks(): List<FavoriteBook>{
        return bookDao.getAllBooks()
    }
    suspend fun getFavoriteBookById(id: String): FavoriteBook?{
        return bookDao.getBookById(id)
    }
    suspend fun updateBook(book: FavoriteBook){
        return bookDao.updateBook(book)
    }


}