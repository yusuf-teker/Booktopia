package com.example.bookfinder.data.repositories

import com.example.bookfinder.BuildConfig
import com.example.bookfinder.data.local.dao.BookDao
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.remote.BookApi
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao
) {
    suspend fun searchBooks(query: String): List<Book> {
        return try {
            val searchResult = bookApi.searchBooks(query, apiKey = BuildConfig.BOOK_FINDER_API_KEY)
            searchResult.items
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteBookFromFavorites(book: FavoriteBook) {
        bookDao.deleteFavoriteBook(book)
    }
    suspend fun insertBookToFavorites(book: FavoriteBook) {
        bookDao.insertFavoriteBook(book)
    }
}