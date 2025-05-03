package com.example.shopfood.data.remote.firebase.repository

import android.util.Log
import com.example.shopfood.domain.model.Order
import com.example.shopfood.domain.model.OrderWithFirebase
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

    override suspend fun getOrdersByUserId(userId: String): Result<List<OrderWithFirebase>> =
        withContext(Dispatchers.IO) {
            try {
                val snapshot = database.getReference("orders")
                    .orderByChild("userId")
                    .equalTo(userId)
                    .get()
                    .await()
                Log.d("FirebaseDebug", "Snapshot: ${snapshot.value}")
                val orders = snapshot.children.mapNotNull { child ->
                    try {
                        val order = child.getValue(OrderWithFirebase::class.java)
                        if (order != null) {
                            order.copy(id = child.key ?: "")
                        } else null
                    } catch (e: Exception) {
                        Log.e("FirebaseDebug", "Parse error: ${e.message}")
                        null
                    }
                }
                Log.d("FirebaseDebug", "order: $orders")
                Result.success(orders)
            } catch (e: Exception) {
                Log.e("FirebaseOrders", "Error: ${e.message}")
                Result.failure(e)
            }
        }

    override suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val orderRef = database.getReference("orders").child(orderId)
                orderRef.child("status").setValue(status).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

}