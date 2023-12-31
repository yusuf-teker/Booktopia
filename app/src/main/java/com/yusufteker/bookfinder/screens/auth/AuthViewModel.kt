package com.yusufteker.bookfinder.screens.auth

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

    private val _passwordText = MutableStateFlow<String>("")
    val passwordText: StateFlow<String> = _passwordText


    private val _emailValidationState = MutableStateFlow<Boolean>(true)
    val emailValidationState: StateFlow<Boolean> = _emailValidationState

    private val _passwordValidationState = MutableStateFlow<Boolean>(true)
    val passwordValidationState: StateFlow<Boolean> = _passwordValidationState

    fun setEmailText(email: String?){
        _emailText.value = email?:""
    }
    fun setPasswordText(password: String?){
        _passwordText.value = password?:""
    }

    fun setEmailValidationState(state: Boolean){
        _emailValidationState.value = state
    }
    fun setPasswordValidationState(state: Boolean){
        _passwordValidationState.value = state
    }




    fun signUp(email: String?, password: String?) {
        if (validateEmail(emailText = email)){
            if (passwordValidationState.value){
                auth.createUserWithEmailAndPassword(email?:"", password?:"")
                    .addOnSuccessListener {
                        _isLoggedIn.value = true
                        _isLoading.value = false
                    }
                    .addOnFailureListener { exception ->
                        _isLoading.value = false
                    }
            }else _passwordValidationState.value = false

        }else _emailValidationState.value = false

    }

    fun login() {
        if (validateEmail(_emailText.value)){
            if (validatePassword(_passwordText.value)){
                _isLoading.value = true
                auth.signInWithEmailAndPassword(_emailText.value, _passwordText.value)
                    .addOnSuccessListener {
                        _isLoggedIn.value = true
                        _isLoading.value = false
                    }
                    .addOnFailureListener { exception ->
                        _isLoading.value = false
                    }
            }else _passwordValidationState.value = false
        }else _emailValidationState.value = false

    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _isLoading.value = false
    }

    fun validateEmail(emailText: String?): Boolean{
        val emailRegex = Regex(pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+")
        return !emailText.isNullOrEmpty() && emailText.matches(emailRegex)
    }
    fun validatePassword(password: String?): Boolean{
        return  !password.isNullOrEmpty() && password.length >= 6
    }

    fun clearScreenState(){
        setEmailValidationState(true)
        setPasswordValidationState(true)
        setPasswordText("")
        setEmailText("")
    }
}