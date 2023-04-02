package com.example.bookfinder.data.remote

import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {


    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String
    ): BookListResponse

    @GET("volumes/{id}")
    suspend fun getBookById(
        @Path("id") id: String,
        @Query("key") apiKey: String
    ): Book


}