package com.example.bookfinder.screens.search.searchDetail

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
class SearchDetailViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel(){

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

     fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }

    fun getBookById(bookId: String){
        viewModelScope.launch {
            _book.value = repository.getBookById(bookId)
            _isFavorite.value = repository.getFavoriteStatus(bookId)
        }
    }

    fun insertFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.insertBookToFavorites(book)
            repository.addOrRemoveBookFromFavorites(bookId = book.id, true)
        }
    }
    fun deleteFavoriteBook(book: FavoriteBook) {
        viewModelScope.launch {
            repository.deleteBookFromFavorites(book)
            repository.addOrRemoveBookFromFavorites(bookId = book.id, false)
        }
    }

    fun deleteFavoriteBookById(bookId: String) {
        viewModelScope.launch {
            repository.deleteBookFromFavoritesById(bookId)
        }
    }
    init {

    }
}