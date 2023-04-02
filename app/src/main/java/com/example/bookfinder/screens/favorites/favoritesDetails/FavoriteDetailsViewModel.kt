package com.example.bookfinder.screens.favorites.favoritesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.MyApplication
import com.example.bookfinder.data.local.BookDatabase
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.room.FavoriteBook

import com.example.bookfinder.data.repositories.FavoritesRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDetailsViewModel  @Inject constructor(
    private val repository: FavoritesRepository
    ) : ViewModel() {

    private val _book = MutableStateFlow<FavoriteBook?>(null)
    val book: StateFlow<FavoriteBook?> = _book

    fun saveFavoriteBook(book: FavoriteBook?) {
        if (book!=null){
            viewModelScope.launch {
                repository.insertBookToFavorites(book)
            }
        }

    }
    fun deleteFavoriteBook(book: FavoriteBook?) {
        if (book != null){
            viewModelScope.launch {
                repository.deleteBookFromFavorites(book)
            }
        }

    }

    fun getFavoriteBookById(id : String){
        viewModelScope.launch {
            _book.value = repository.getFavoriteBookById(id)
        }
    }

    init {

    }


}