package com.example.shopfood.domain.usecase.firebase.home.user

import com.example.shopfood.domain.repository.firebase.UserRepository

class DeleteAddress(private val repository: UserRepository) {
    suspend operator fun invoke(userId: String, addressId: String): Result<Unit> {
        return repository.deleteAddress(userId, addressId)
    }
}