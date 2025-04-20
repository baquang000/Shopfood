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
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _loginState = MutableStateFlow<AuthResult?>(null)
    val loginState: StateFlow<AuthResult?> = _loginState

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onEmailChange(new: String) {
        email = new.trim()
    }

    fun onPasswordChange(new: String) {
        password = new
    }

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = AuthResult.Failure("Please enter both email and password")
            return
        }

        viewModelScope.launch {
            authUseCases.login(email, password).collect { result ->
                _loginState.value = result
            }
        }
    }

    fun resetState() {
        _loginState.value = null
    }
}