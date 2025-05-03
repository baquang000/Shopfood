package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.Order
import com.example.shopfood.domain.model.OrderWithFirebase

interface OrderRepository {
    suspend fun saveOrder(order: Order): Result<Unit>
    suspend fun getOrdersByUserId(userId: String): Result<List<OrderWithFirebase>>
    suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit>
}