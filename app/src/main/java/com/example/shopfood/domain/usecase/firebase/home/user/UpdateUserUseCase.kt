package com.example.shopfood.domain.usecase.firebase.home.user

import com.example.shopfood.domain.model.User
import com.example.shopfood.domain.repository.firebase.UserRepository

class UpdateUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return repository.updateUser(user)
    }
}