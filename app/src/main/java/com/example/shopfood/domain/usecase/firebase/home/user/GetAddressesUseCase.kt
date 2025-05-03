package com.example.shopfood.domain.usecase.firebase.home.user

import com.example.shopfood.domain.repository.firebase.UserRepository

class GetAddressesUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(userId: String) = repo.getAddresses(userId)
}