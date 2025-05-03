package com.example.shopfood.data.remote.firebase.repository

import com.example.shopfood.domain.model.Address
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

    override suspend fun addAddress(userId: String, address: Address): Result<Unit> {
        return try {
            val ref = database.getReference("users").child(userId).child("addresses").push()
            val addressWithId = address.copy(id = ref.key ?: "")
            ref.setValue(addressWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getAddresses(userId: String): Result<List<Address>> {
        return try {
            val snapshot =
                database.getReference().child("users").child(userId).child("addresses").get()
                    .await()
            val list = snapshot.children.mapNotNull { it.getValue(Address::class.java) }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAddress(userId: String, addressId: String): Result<Unit> {
        return try {
            val ref = database.getReference("users").child(userId).child("addresses").child(addressId)
            ref.removeValue().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
