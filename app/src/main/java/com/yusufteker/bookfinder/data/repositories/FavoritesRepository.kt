package com.yusufteker.bookfinder.data.repositories

import android.util.Log
import com.yusufteker.bookfinder.data.local.dao.BookDao
import com.yusufteker.bookfinder.data.model.remote.FirebaseBook
import com.yusufteker.bookfinder.data.model.room.FavoriteBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject
import com.yusufteker.bookfinder.BuildConfig
import com.yusufteker.bookfinder.data.local.dao.NoteDao
import com.yusufteker.bookfinder.data.model.remote.Book
import com.yusufteker.bookfinder.data.model.room.Note
import com.yusufteker.bookfinder.data.remote.BookApi

class FavoritesRepository @Inject constructor(
    private val bookDao: BookDao,
    private val noteDao: NoteDao,
    private val bookApi: BookApi,
) {

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userId = currentUser?.uid?:""

    suspend fun deleteBookFromFavorites(book: FavoriteBook) {
        bookDao.deleteFavoriteBook(book)
    }
    suspend fun insertBookToFavorites(book: FavoriteBook) {
        bookDao.insertFavoriteBook(book)
    }
    suspend fun findFavotireBooks(query: String): List<FavoriteBook>{
        return bookDao.findFavotireBooks(query) ?: emptyList()
    }
    suspend fun findBooksByReadingStatus(readingStatus: Int): List<FavoriteBook>{
        return bookDao.findBooksByReadingStatus(readingStatus) ?: emptyList()
    }

    suspend fun deleteBookFromFavoritesById(bookId: String) {
        bookDao.deleteFavoriteBookById(bookId)
    }
    suspend fun getAllFavoriteBooks(): List<FavoriteBook>{
        return bookDao.getAllBooks()
    }
    suspend fun getFavoriteBookById(id: String): FavoriteBook?{
        return bookDao.getBookById(id)
    }
    suspend fun updateBook(book: FavoriteBook){
        return bookDao.updateBook(book)
    }

    suspend fun addNoteToBook(note: Note){
        return noteDao.addNoteToBook(note)
    }

    suspend fun deleteNote(note: Note){
        return noteDao.deleteNote(note)
    }

    suspend fun getNotesForBook(bookId: String): List<Note>{
        return noteDao.getNotesForBook(bookId)
    }

    fun addOrRemoveBookFromFavorites(bookId: String, isFavorite: Boolean) {
        val usersRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("users")
        val userFavoritesRef = usersRef.child(userId).child("favoriteBooks")
        val bookRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books").child(bookId)

        bookRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val book = mutableData.getValue(FirebaseBook::class.java)

                if (book == null) {
                    // If the book doesn't exist in the database -> create it with favoriteCount = 1 and readCount = 0
                    val newBook = FirebaseBook(bookId, 0, 1)
                    mutableData.value = newBook
                } else {
                    // If the book exists in the database,
                    // if the book is being favorited -> increment the count
                    // if the book is being unfavorited -> decrement the favorite count
                    if (isFavorite) {
                        book.favoriteCount++
                    } else {
                        if (book.favoriteCount>0){
                            book.favoriteCount--
                            if (book.favoriteCount == 0 && book.readCount == 0){
                                bookRef.removeValue()
                            }else{
                                mutableData.value = book
                            }
                        }

                    }

                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                if (databaseError == null) {
                    // If the transaction was successful, add or remove the book from the user's favorites list
                    if (isFavorite) {
                        userFavoritesRef.child(bookId).setValue(true)
                        Log.d("yusuf", "Added the book to favorites")
                    } else {
                        userFavoritesRef.child(bookId).removeValue()
                        Log.d("yusuf", "Removed the book from favorites")
                    }
                } else {
                    // If the transaction failed, log the error
                    Log.d("yusuf", "Error adding or removing book from favorites")
                }
            }
        })
    }
    fun addBookToReadBooks(bookId: String) {
        val usersRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("users")
        val userReadBooksRef = usersRef.child(userId).child("readBooks")
        val bookRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books").child(bookId)
        bookRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val book = mutableData.getValue(FirebaseBook::class.java)
                if (book == null) {
                    // If the book doesn't exist in the database, -> create it with favoriteCount = 1 and readCount = 1
                    val newBook = FirebaseBook(bookId, 1, 1)
                    mutableData.value = newBook
                    userReadBooksRef.child(bookId).setValue(true)
                } else {
                    // If the book exist in the database -> increment the readCount
                    book.readCount++
                    mutableData.value = book
                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(
                databaseError: DatabaseError?,
                b: Boolean,
                dataSnapshot: DataSnapshot?
            ) {
                if (databaseError == null) {
                    // bookRef readCount incremented, now userReadBooksRef'de bookId will be added
                    userReadBooksRef.child(bookId).setValue(true)
                } else {
                    // ERROR
                    Log.d("yusuf","bookRef couldn't addBookToReadBooks")
                }
            }
        })

    }


    fun decreaseReadCount(bookId: String) {
        val userRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("users").child(userId).child("readBooks").child(bookId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //User has already marked this book as read
                    val bookRef = FirebaseDatabase.getInstance(BuildConfig.BOOKTOPIA_FIREBASE_URL).getReference("books").child(bookId)
                    bookRef.runTransaction(object : Transaction.Handler {
                        override fun doTransaction(mutableData: MutableData): Transaction.Result {
                            val book = mutableData.getValue(FirebaseBook::class.java)

                            if (book == null) {
                                // If the book does not exist, create a new book
                                mutableData.value = FirebaseBook(bookId, 0, 1)
                            }
                            else {
                                // Decrease readCount if book exists
                                book.readCount = book.readCount - 1
                                mutableData.value = book
                            }
                            return Transaction.success(mutableData)
                        }

                        override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                            //Decreasing ReadCount successful
                        }
                    })
                }
                userRef.removeValue() // remove Books from user's node
            }

            override fun onCancelled(error: DatabaseError) {
                // ERROR
                Log.d("yusuf","bookRef couldn't decreaseReadCount")
            }
        })
    }

    fun getAllBooksForUser(onBookIdsFetch: (List<String>) -> Unit) {
        val userBooksRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("favoriteBooks")

        userBooksRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookIds = mutableListOf<String>()
                for (bookSnapshot in snapshot.children) {
                    val bookId = bookSnapshot.key ?: continue
                    bookIds.add(bookId)

                }
                onBookIdsFetch(bookIds)


            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }
    suspend fun getBooksByIdList(bookIdList: List<String>): List<Book>{
        return try {
            val filteredBookList = bookIdList.filter { !it.contains("-") && !it.contains("_")}
            bookApi.getBooksByIds(filteredBookList.joinToString("|"), filteredBookList.size).items
        }catch (e: java.lang.Exception){
            listOf()
        }
    }

    suspend fun saveBooksToDatabase(books: List<FavoriteBook>) {
        bookDao.insertBooks(books)
    }
}