package com.example.bookfinder.screens.favorites.favoriteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.toFavoriteBook
import com.example.bookfinder.data.model.room.FavoriteBook
import com.example.bookfinder.data.repositories.FavoritesRepository
import com.example.bookfinder.util.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val repository: FavoritesRepository,
    private val userPreferencesManager: UserPreferencesManager
): ViewModel(){


    private val _books = MutableStateFlow<List<FavoriteBook>>(emptyList())
    val books: StateFlow<List<FavoriteBook>> = _books


    private val _shouldShowInfoPopUp = MutableStateFlow(false)
    val shouldShowInfoPopUp: StateFlow<Boolean> = _shouldShowInfoPopUp

    private val _searchAppBarState = MutableStateFlow(SearchAppBarState.CLOSED)
    val searchAppBarState: StateFlow<SearchAppBarState> = _searchAppBarState

    private val _searchTextState = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState



    fun setSearchAppBarState(status: SearchAppBarState){
        _searchAppBarState.value = status
    }
    fun setSearchTextState(searchText: String){
        _searchTextState.value = searchText
    }

    fun hidePopUpTemporary(){
        _shouldShowInfoPopUp.value = false
    }
    fun neverShowInfoPopUpAgain(){
        viewModelScope.launch {
            userPreferencesManager.setShouldShowInfoPopUp(false)
        }
    }
    fun getAllFavoriteBooks() {
        viewModelScope.launch {
            _books.value = repository.getAllFavoriteBooks()
        }
    }

    fun findFavoriteBooks(query: String){
        viewModelScope.launch {
            _books.value = repository.findFavotireBooks(query)
        }
    }

    fun findBooksByReadingStatus(readingStatus: Int){
        viewModelScope.launch {
            _books.value = repository.findBooksByReadingStatus(readingStatus)
        }
    }

    fun insertFavoriteBook(book: FavoriteBook?) {
        if (book!=null){
            viewModelScope.launch {
                repository.insertBookToFavorites(book)
                repository.addOrRemoveBookFromFavorites(bookId = book.bookId, true)
            }
        }

    }
    fun deleteFavoriteBook(book: FavoriteBook?) {
        if (book != null){
            viewModelScope.launch {
                repository.deleteBookFromFavorites(book)
                repository.addOrRemoveBookFromFavorites(bookId = book.bookId, false)
                getAllFavoriteBooks()
            }
        }
    }
    private fun getShouldShowInfoPopUp(){
        viewModelScope.launch {
            userPreferencesManager.getShouldShowInfoPopUp.collectLatest {
                _shouldShowInfoPopUp.value = it ?: false
            }
        }
    }

    fun syncFavoriteBooks(){
        viewModelScope.launch {
            repository.getAllBooksForUser(){
                viewModelScope.launch{
                    _books.value = mergeFavoriteBookLists(_books.value , booksToFavoriteBooks(repository.getBooksByIdList(it)))
                    repository.saveBooksToDatabase(_books.value)
                }

            }
        }
    }
    fun mergeFavoriteBookLists(favoriteBooksList1: List<FavoriteBook>, favoriteBooksList2: List<FavoriteBook>) : List<FavoriteBook>{

        val mergedFavoriteBooks = mutableListOf<FavoriteBook>()

        mergedFavoriteBooks.addAll(favoriteBooksList1)

        for (book in favoriteBooksList2) {
            val isAlreadyAdded = mergedFavoriteBooks.any { it.bookId == book.bookId }
            if (!isAlreadyAdded) {
                mergedFavoriteBooks.add(book)
            }
        }

       return mergedFavoriteBooks
    }

    fun booksToFavoriteBooks(bookList: List<Book>): List<FavoriteBook>{
        return bookList.map { book -> book.toFavoriteBook() }
    }

    init {
        getAllFavoriteBooks()
        getShouldShowInfoPopUp()
    }
}
enum class SearchAppBarState {
    CLOSED,
    OPENED
}