package com.example.bookfinder.screens.favorites.favoritesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.model.room.Note

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

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes


    private val _selectedBox = MutableStateFlow(_book.value?.readingStatus ?: -1)
    val selectedBox: StateFlow<Int> = _selectedBox

    private val _isFavorite = MutableStateFlow(true)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun saveFavoriteBook(book: FavoriteBook?) {
        if (book!=null){
            viewModelScope.launch {
                repository.insertBookToFavorites(book)
                repository.addOrRemoveBookFromFavorites(bookId = book.bookId, true)
            }
        }
    }

    fun deleteFavoriteBookById(bookId: String) {
        viewModelScope.launch {
            repository.deleteBookFromFavoritesById(bookId)
            repository.addOrRemoveBookFromFavorites(bookId,false)
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
                repository.addOrRemoveBookFromFavorites(bookId = book.bookId,false)
            }
        }

    }

    fun getFavoriteBookById(id : String){
        viewModelScope.launch {
            _book.value = repository.getFavoriteBookById(id)
            _selectedBox.value = _book.value?.readingStatus ?: -1
        }
    }
    fun getNotesByBookId(id : String){
        viewModelScope.launch {
           _notes.value = repository.getNotesForBook(id)
        }
    }
    fun updateBook(){
        viewModelScope.launch {
            repository.updateBook(
                _book.value!!.copy(
                        readingStatus = selectedBox.value
                    )
            )
            if (selectedBox.value == 2){
                repository.addBookToReadBooks(_book.value!!.bookId)
            }else{
                repository.decreaseReadCount(_book.value!!.bookId)
            }
            if (_book.value!!.isFavorite){
                repository.addOrRemoveBookFromFavorites(_book.value!!.bookId,true)
            }

        }
    }

    fun addNoteToBook(note: Note){
        viewModelScope.launch {
            repository.addNoteToBook(note)
            repository.getNotesForBook(note.bookId)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun setSelectedBox(selectedIndex: Int){
        _selectedBox.value = selectedIndex
    }

}