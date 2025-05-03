package com.example.shopfood.domain.usecase.firebase.home.user

import com.example.shopfood.domain.model.Address
import com.example.shopfood.domain.repository.firebase.UserRepository

class AddAddressUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(userId: String, address: Address) = repo.addAddress(userId, address)
}