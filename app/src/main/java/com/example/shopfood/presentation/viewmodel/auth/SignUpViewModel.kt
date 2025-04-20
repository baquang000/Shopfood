package com.example.shopfood.presentation.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.AuthResult
import com.example.shopfood.domain.usecase.firebase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _signInState = MutableStateFlow<AuthResult?>(null)
    val signInState: StateFlow<AuthResult?> = _signInState

    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var rePassword by mutableStateOf("")
        private set

    var error by mutableStateOf(false)
        private set

    fun onNameChange(new: String) {
        name = new
    }

    fun onEmailChange(new: String) {
        email = new.trim()
    }

    fun onPasswordChange(new: String) {
        password = new
    }

    fun onRePasswordChange(new: String) {
        rePassword = new
    }

    fun signUp() {
        if (!validateForm()) return
        createUserInFirebase(
            name = name,
            email = email,
            password = password
        )
    }

    private fun validateForm(): Boolean {
        error = password != rePassword
        return !error
    }

    private fun createUserInFirebase(name: String, email: String, password: String) {
        viewModelScope.launch {
            authUseCases.signup(name, email, password).collect { result ->
                _signInState.value = result
            }
        }
    }

    fun resetState() {
        _signInState.value = null
    }
}