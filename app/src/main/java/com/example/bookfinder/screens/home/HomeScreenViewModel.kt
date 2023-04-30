package com.example.bookfinder.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.repositories.HomeRepository
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

    private val _newestBooks = MutableStateFlow<List<Book>>(emptyList())
    val newestBooks: StateFlow<List<Book>> = _newestBooks

    init {
        viewModelScope.launch {
            _newestBooks.value = repository.getNewestBooks()
        }

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