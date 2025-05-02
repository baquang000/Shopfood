package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.Order

interface OrderRepository {
    suspend fun saveOrder(order: Order): Result<Unit>
}