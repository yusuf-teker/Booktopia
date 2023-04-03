package com.example.bookfinder.screens.favorites.favoritesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _noteText = MutableStateFlow<String>("")
    val noteText: StateFlow<String> = _noteText

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
            _noteText.value = _book.value?.myNotes ?: ""
        }
    }

    fun updateBook(){
        viewModelScope.launch {
            repository.updateBook(_book.value!!.copy(myNotes = noteText.value))

        }
    }

    fun setNoteText(noteText: String){
        _noteText.value = noteText
    }
    init {

    }


}