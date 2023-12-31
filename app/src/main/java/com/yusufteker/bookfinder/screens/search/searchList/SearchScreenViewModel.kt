package com.yusufteker.bookfinder.screens.search.searchList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yusufteker.bookfinder.data.model.room.FavoriteBook
import com.yusufteker.bookfinder.data.pagination.BookPagingSource
import com.yusufteker.bookfinder.data.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private lateinit var pagingSource : BookPagingSource

    val bookPager = Pager(PagingConfig(pageSize = 10)) {
        BookPagingSource(query.value, repository).also { pagingSource = it }
    }.flow

    fun invalidateDataSource() {
        pagingSource.invalidate()
    }

    fun insertFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.insertBookToFavorites(book)
            repository.addOrRemoveBookFromFavorites(bookId = book.bookId, true)
        }
    }
    fun deleteFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.deleteBookFromFavorites(book)
            repository.addOrRemoveBookFromFavorites(book.bookId, false)
        }
    }
    init {

    }
    fun setQueryAndSearch(query: String) {
        invalidateDataSource()
        _query.value = query
    }
    fun clearQuery() {
        _query.value = ""
    }
}