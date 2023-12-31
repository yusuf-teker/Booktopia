package com.yusufteker.bookfinder.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.data.pagination.NewestPagingSource
import com.yusufteker.bookfinder.data.repositories.HomeRepository
import com.yusufteker.bookfinder.util.maxBookCountForApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _mostFavoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val mostFavoriteBooks: StateFlow<List<Book>> = _mostFavoriteBooks

    private val _mostReadedBooks = MutableStateFlow<List<Book>>(emptyList())
    val mostReadedBooks: StateFlow<List<Book>> = _mostReadedBooks

    private lateinit var pagingSource : NewestPagingSource

    val newestBookPager = Pager(PagingConfig(pageSize = maxBookCountForApi)) {
        NewestPagingSource( repository).also { pagingSource = it }
    }.flow
    init {

        repository.getMostReadedBooksFromFirebase() {
            viewModelScope.launch() {
                _mostReadedBooks.value = repository.getBooksByIdList(it)
            }
        }
        repository.getMostFavoritedBooksFromFirebase(){
            viewModelScope.launch {
                _mostFavoriteBooks.value = repository.getBooksByIdList(it)
            }
        }

    }

}