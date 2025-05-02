package com.example.shopfood.domain.usecase.firebase.home.user

import com.example.shopfood.domain.model.User
import com.example.shopfood.domain.repository.firebase.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(userId: String): User? {
        return repository.getUser(userId)
    }
}