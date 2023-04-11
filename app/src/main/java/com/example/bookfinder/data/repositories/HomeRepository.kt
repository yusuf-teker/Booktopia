package com.example.bookfinder.data.repositories

import com.example.bookfinder.data.model.remote.Book
import com.example.bookfinder.data.model.remote.FirebaseBook
import com.example.bookfinder.data.remote.BookApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val bookApi: BookApi,
) {

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    fun getUserId(): String{
        return  currentUser?.uid?:""
    }


    fun getMostFavoriteBooks(): List<String>{
        return listOf()
    }
    fun getMostReadedBooks(): List<String>{
        return listOf()
    }
    suspend fun getNewestBooks(): List<Book>{
        return try {
            bookApi.getNewReleases()?.items?: emptyList<Book>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getBooksByIdList(bookIdList: List<String>): List<Book>{
        //fetch 10 book
        return listOf()
    }

    fun addOrRemoveBookFromFavorites(bookId: String, isFavorite: Boolean) {
        // get the user's favorites list
        val userFavoritesRef = Firebase.database.reference.child("users").child(getUserId()).child("favorites")
        //addListenerForSingleValueEvent 1 seferlik veriyi alır/update eder sonra trafik kapanır. Sürekli observe etmez
        userFavoritesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // check if the bookId already exists in the favorites list
                val isBookFavorite = snapshot.child(bookId).exists()

                if (isFavorite && !isBookFavorite) { // add the book to favorites
                    userFavoritesRef.child(bookId).setValue(true)
                } else if (!isFavorite && isBookFavorite) { // remove the book from favorites
                    userFavoritesRef.child(bookId).removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }

    // Kitap okununca bu fonksiyon çağrılabilir
    fun increaseReadCount(bookId: String) {
        val bookRef = FirebaseDatabase.getInstance().getReference("books").child(bookId)
        //runTransaction aynı anda 2 kullanıcın aynı datayı değiştirmesini ve sıkıntı olmasıı engeller
        bookRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val book = mutableData.getValue(FirebaseBook::class.java)
                if (book == null) {
                    // Kitap yoksa, yeni bir kitap oluştur
                    mutableData.setValue(FirebaseBook(bookId, 1,1           ))
                } else {
                    // Kitap varsa, readCount'u artır
                    book.readCount = book.readCount + 1
                    mutableData.setValue(book)
                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                // İşlem tamamlandığında yapılacaklar
            }
        })
    }

    // Kitap okunduğundan kaldırılırsa bu fonksiyon çağrılabilir
    fun decreaseReadCount(bookId: String) {
        val bookRef = FirebaseDatabase.getInstance().getReference("books").child(bookId)
        bookRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val book = mutableData.getValue(FirebaseBook::class.java)
                if (book != null) {
                    // Kitap varsa, readCount'u azalt
                    book.readCount = book.readCount - 1
                    mutableData.setValue(book)
                }
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                // İşlem tamamlandığında yapılacaklar
            }
        })
    }



}
