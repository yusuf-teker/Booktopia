package com.example.bookfinder.data.remote

import com.example.bookfinder.BuildConfig
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.BookListResponse
import com.example.bookfinder.util.maxBookCountForApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {


    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = maxBookCountForApi,
        @Query("key") apiKey: String
    ): BookListResponse

    @GET("volumes/{id}")
    suspend fun getBookById(
        @Path("id") id: String,
        @Query("key") apiKey: String
    ): Book

    @GET("volumes")
    suspend fun getBooksByIds(@Query("q") ids: String,@Query("maxResults") maxResults: Int): BookListResponse

    @GET("volumes")
    suspend fun getNewReleases(
        @Query("q") query: String = "inauthor:",
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String = BuildConfig.BOOK_FINDER_API_KEY,
        @Query("orderBy") orderBy: String = "newest",
        @Query("printType") printType: String = "books",
        @Query("projection") projection: String = "full"
    ): BookListResponse

}