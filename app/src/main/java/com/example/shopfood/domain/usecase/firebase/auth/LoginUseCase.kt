package com.example.shopfood.domain.usecase.firebase.auth

import com.example.shopfood.domain.model.AuthResult
import com.example.shopfood.domain.repository.firebase.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<AuthResult> = repository.login(email, password)
}