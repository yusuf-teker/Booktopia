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


    private val _selectedBox = MutableStateFlow<Int>(_book.value?.readingStatus ?: -1)
    val selectedBox: StateFlow<Int> = _selectedBox

    private val _isFavorite = MutableStateFlow<Boolean>(true)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun saveFavoriteBook(book: FavoriteBook?) {
        if (book!=null){
            viewModelScope.launch {
                repository.insertBookToFavorites(book)
            }
        }
    }

    fun deleteFavoriteBookById(bookId: String) {
        viewModelScope.launch {
            repository.deleteBookFromFavoritesById(bookId)
        }
    }

    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }

    fun deleteFavoriteBook(book: FavoriteBook?) {
        if (book != null){
            viewModelScope.launch {
                repository.deleteBookFromFavorites(book)
                _isFavorite.value = false
            }
        }

    }

    fun getFavoriteBookById(id : String){
        viewModelScope.launch {
            _book.value = repository.getFavoriteBookById(id)
            _noteText.value = _book.value?.myNotes ?: ""
            _selectedBox.value = _book.value?.readingStatus ?: -1
        }
    }

    fun updateBook(){
        viewModelScope.launch {
            repository.updateBook(
                _book.value!!.copy(
                        myNotes = noteText.value,
                        readingStatus = selectedBox.value
                    )
            )

        }
    }

    fun setNoteText(noteText: String){
        _noteText.value = noteText
    }

    fun setSelectedBox(selectedIndex: Int){
        _selectedBox.value = selectedIndex
    }

    init {

    }


}