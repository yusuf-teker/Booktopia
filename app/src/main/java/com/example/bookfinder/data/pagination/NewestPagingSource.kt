package com.example.bookfinder.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.repositories.HomeRepository
import com.example.bookfinder.util.maxBookCountForApi


class NewestPagingSource(
    private val repo: HomeRepository
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val pageNumber = params.key ?: 0
            val response = repo.getNewestBooks(pageNumber, maxBookCountForApi)
            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (response.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
