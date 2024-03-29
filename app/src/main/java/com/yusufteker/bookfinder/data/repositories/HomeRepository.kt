package com.yusufteker.bookfinder.data.repositories

import android.util.Log
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.data.remote.BookApi
import com.google.firebase.database.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val bookApi: BookApi,
) {

    suspend fun getNewestBooks(page: Int, perPage: Int): List<Book>{
        return try {
            bookApi.getNewReleases(startIndex = page, maxResults = perPage ).items
        } catch (e: Exception) {
            emptyList()
        }
    }

     suspend fun getBooksByIdList(bookIdList: List<String>): List<Book>{
        return try {
            val filteredBookList = bookIdList.filter { !it.contains("-") && !it.contains("_")}
            bookApi.getBooksByIds(filteredBookList.joinToString("|"), filteredBookList.size).items
        }catch (e: java.lang.Exception){
            listOf()
        }
    }

      fun getMostReadedBooksFromFirebase(onMostReadedBooksFetch:(List<String>)-> Unit){
        val booksRef = FirebaseDatabase.getInstance(com.yusufteker.bookfinder.BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books")
        val topReadBooksQuery = booksRef.orderByChild("readCount").limitToLast(10)

        topReadBooksQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val topReadBooksIds = mutableListOf<String>()
                for (bookSnapshot in snapshot.children.reversed()) {
                    val bookId = bookSnapshot.key ?: continue
                    val readCount = bookSnapshot.child("readCount").getValue(Long::class.java) ?: 0
                    if (readCount > 0) {
                        topReadBooksIds.add(bookId)
                    }
                }
                onMostReadedBooksFetch(topReadBooksIds.toList())
                // topReadBooksIds -> top 10 most read books
                Log.d("yusuf","Top read books IDs: $topReadBooksIds")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yusuf","Error fetching top read books IDs: ${error.message}")
            }
        })
    }

    fun getMostFavoritedBooksFromFirebase(onMostFavoritedBooksFetch:(List<String>)-> Unit){
        val booksRef = FirebaseDatabase.getInstance(com.yusufteker.bookfinder.BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books")
        val topReadBooksQuery = booksRef.orderByChild("favoriteCount").limitToLast(10)

        topReadBooksQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val topFavoritedBooksIds = mutableListOf<String>()
                for (bookSnapshot in snapshot.children.reversed()) {
                    val bookId = bookSnapshot.key ?: continue
                    val favoriteCount = bookSnapshot.child("favoriteCount").getValue(Long::class.java) ?: 0
                    if (favoriteCount > 0) {
                        topFavoritedBooksIds.add(bookId)
                    }
                }
                onMostFavoritedBooksFetch(topFavoritedBooksIds.toList())
                // topReadBooksIds -> top 10 most read books
                Log.d("yusuf","Top favorited books IDs: $topFavoritedBooksIds")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yusuf","Error fetching top read books IDs: ${error.message}")
            }
        })
    }

}
