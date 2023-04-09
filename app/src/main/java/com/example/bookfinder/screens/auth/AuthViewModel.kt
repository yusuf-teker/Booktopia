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

    private val _emailText = MutableStateFlow<String>("")
    val emailText: StateFlow<String> = _emailText

    fun setEmailText(email: String?){
        _emailText.value = email?:""
    }
    fun setPasswordText(password: String?){
        _passwordText.value = password?:""
    }

    private val _passwordText = MutableStateFlow<String>("")
    val passwordText: StateFlow<String> = _passwordText

    private val _isError  = MutableStateFlow<Boolean>(false)
    val isError: StateFlow<Boolean> = _isError

    fun signUp(email: String?, password: String?) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()){
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
    }

    fun login() {

        if (!_emailText.value.isNullOrEmpty() && !_passwordText.value.isNullOrEmpty()){
            _isLoading.value = true
            auth.signInWithEmailAndPassword(_emailText.value, _passwordText.value)
                .addOnSuccessListener {
                    _isLoggedIn.value = true
                    _isLoading.value = false
                }
                .addOnFailureListener { exception ->
                    _isLoading.value = false
                }
        }

    }

    //todo başka ekranda kullanacağım
    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _isLoading.value = false
    }
}