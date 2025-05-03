package com.example.shopfood.domain.usecase.firebase.home.order

import com.example.shopfood.domain.model.OrderWithFirebase
import com.example.shopfood.domain.repository.firebase.OrderRepository

class GetOrdersByUserIdUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(userId: String): Result<List<OrderWithFirebase>> {
        return repository.getOrdersByUserId(userId)
    }
}