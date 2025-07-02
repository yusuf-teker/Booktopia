package com.yusufteker.bookfinder.data.repositories

import android.util.Log
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.data.remote.BookApi
import com.google.firebase.database.*
import com.yusufteker.bookfinder.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
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

    suspend fun getBooksByIdList(rawIds: List<String>): List<Book> = supervisorScope {
        Log.d("yusuf", "RAW IDS  -> $rawIds")

        val ids = rawIds
            .map { it.substringAfterLast('-').substringAfterLast('_') }
            .filter { it.isNotBlank() }

        Log.d("yusuf", "CLEAN IDS -> $ids")

        ids.map { id ->
            async(Dispatchers.IO) {  // 👈 burada IO dispatcher kullanıyoruz
                runCatching {
                    Log.d("yusuf", "CALL       -> $id")
                    var x: Book? =null
                    try {
                        x   = bookApi.getBookById(id, BuildConfig.BOOK_FINDER_API_KEY)
                    }catch (e:Exception){

                    }

                    Log.d("yusuf", "BOOK       -> $x")
                    x
                }.getOrNull()
            }
        }.awaitAll()
            .filterNotNull()
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
