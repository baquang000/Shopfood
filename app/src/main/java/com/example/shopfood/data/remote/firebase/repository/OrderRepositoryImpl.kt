package com.example.shopfood.data.remote.firebase.repository

import com.example.shopfood.domain.model.Order
import com.example.shopfood.domain.repository.firebase.OrderRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : OrderRepository {
    override suspend fun saveOrder(order: Order): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val orderId = database.getReference("orders").push().key
                ?: return@withContext Result.failure(Exception("Cannot generate order ID"))

            database.getReference("orders").child(orderId).setValue(order).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}