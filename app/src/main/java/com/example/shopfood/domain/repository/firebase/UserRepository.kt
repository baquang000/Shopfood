package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.Address
import com.example.shopfood.domain.model.User

interface UserRepository {
    suspend fun getUser(userId: String): User?
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun addAddress(userId: String, address: Address): Result<Unit>
    suspend fun getAddresses(userId: String): Result<List<Address>>
    suspend fun deleteAddress(userId: String, addressId: String): Result<Unit>
}