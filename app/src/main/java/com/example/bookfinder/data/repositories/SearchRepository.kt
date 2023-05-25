package com.example.bookfinder.data.repositories

import android.util.Log
import com.example.bookfinder.BuildConfig
import com.example.bookfinder.data.local.dao.BookDao
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.FirebaseBook
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.remote.BookApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao
) {
    suspend fun searchBooks(query: String, page : Int, pageSize: Int): List<Book> {
        return try {
            val searchResult = bookApi.searchBooks(query, startIndex = page*10, apiKey = BuildConfig.BOOK_FINDER_API_KEY, maxResults = pageSize)
            searchResult.items
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteBookFromFavorites(book: FavoriteBook) {
        bookDao.deleteFavoriteBook(book)
    }

    suspend fun deleteBookFromFavoritesById(bookId: String) {
        bookDao.deleteFavoriteBookById(bookId)
    }
    suspend fun insertBookToFavorites(book: FavoriteBook) {
        bookDao.insertFavoriteBook(book)
    }

    suspend fun getBookById(bookId: String): Book?{
        return try {
            val result  = bookApi.getBookById(bookId, apiKey = BuildConfig.BOOK_FINDER_API_KEY)
            result
        }catch (e: Exception){
            null
        }
    }

    suspend fun getFavoriteStatus(bookId: String): Boolean{
        return bookDao.getFavoriteStatus(bookId = bookId)?:false
    }

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userId = currentUser?.uid?:""

    fun addOrRemoveBookFromFavorites(bookId: String, isFavorite: Boolean) {
        val usersRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("users")
        val userFavoritesRef = usersRef.child(userId).child("favoriteBooks")
        val bookRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books").child(bookId)

        bookRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val book = mutableData.getValue(FirebaseBook::class.java)

                if (book == null) {
                    // If the book doesn't exist in the database -> create it with favoriteCount = 1 and readCount = 0
                    val newBook = FirebaseBook(bookId, 0, 1)
                    mutableData.value = newBook
                } else {
                    // If the book exists in the database,
                    // if the book is being favorited -> increment the count
                    // if the book is being unfavorited -> decrement the favorite count
                    if (isFavorite) {
                        book.favoriteCount++
                    } else {
                        if (book.favoriteCount>0){
                            book.favoriteCount--
                            if (book.favoriteCount == 0 && book.readCount == 0){
                                bookRef.removeValue()
                            }else{
                                mutableData.value = book
                            }
                        }

                    }
                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                if (databaseError == null) {
                    // If the transaction was successful, add or remove the book from the user's favorites list
                    if (isFavorite) {
                        userFavoritesRef.child(bookId).setValue(true)
                        Log.d("yusuf", "Added the book to favorites")
                    } else {
                        userFavoritesRef.child(bookId).removeValue()
                        Log.d("yusuf", "Removed the book from favorites")
                    }
                } else {
                    // If the transaction failed, log the error
                    Log.d("yusuf", "Error adding or removing book from favorites")
                }
            }
        })
    }
}