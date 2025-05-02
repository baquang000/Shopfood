package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.User

interface UserRepository {
    suspend fun getUser(userId: String): User?
    suspend fun updateUser(user: User): Result<Unit>
}