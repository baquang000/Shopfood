package com.example.shopfood.domain.model

import com.google.firebase.auth.FirebaseUser

sealed class AuthResult {
    data class SuccessMessage(val message: String) : AuthResult()
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Failure(val error: String) : AuthResult()
    object Loading : AuthResult()
}