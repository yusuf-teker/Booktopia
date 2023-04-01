package com.example.bookfinder.data.remote

import com.example.bookfinder.data.model.remote.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {


    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String
    ): BookResponse

}