package com.example.shopfood.domain.repository.firebase


import com.example.shopfood.domain.model.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<AuthResult>
    fun signup(name: String, email: String, password: String): Flow<AuthResult>
}