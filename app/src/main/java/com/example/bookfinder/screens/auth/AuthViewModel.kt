package com.example.bookfinder.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow<Boolean>(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError  = MutableStateFlow<Boolean>(false)
    val isError: StateFlow<Boolean> = _isError

    fun signUp(email: String?, password: String?) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email?:"", password?:"")
            .addOnSuccessListener {
                _isLoggedIn.value = true
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _isLoading.value = false
            }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _isLoading.value = false
            }
    }

    //todo başka ekranda kullanacağım
    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _isLoading.value = false
    }
}