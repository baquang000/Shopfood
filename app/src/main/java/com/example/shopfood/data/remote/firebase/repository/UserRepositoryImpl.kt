package com.example.shopfood.data.remote.firebase.repository

import com.example.shopfood.domain.model.User
import com.example.shopfood.domain.repository.firebase.UserRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: FirebaseDatabase
) : UserRepository {
    override suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
        val snapshot = database.getReference("users").child(userId).get().await()
        return@withContext snapshot.getValue(User::class.java)
    }

    override suspend fun updateUser(user: User): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            database.getReference("users").child(user.id).setValue(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
