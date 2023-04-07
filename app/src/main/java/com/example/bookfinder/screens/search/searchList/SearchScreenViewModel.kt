package com.example.bookfinder.screens.search.searchList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun searchBooks() {
        _loading.value = true // set loading to true
        viewModelScope.launch {
            try {

                _books.value = repository.searchBooks(_query.value)
            } catch (e: Exception) {
                Log.e("SearchScreenViewModel", "Error searching books", e)
            } finally {
                _loading.value = false // set loading to false
            }
        }

    }

    fun insertFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.insertBookToFavorites(book)
        }
    }
    fun deleteFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.deleteBookFromFavorites(book)
        }
    }
    init {
        searchBooks()
    }
    fun setQueryAndSearch(query: String) {
        _query.value = query
        searchBooks()
    }
}