package com.example.shopfood.domain.usecase.firebase.home.order

import com.example.shopfood.domain.repository.firebase.OrderRepository

class UpdateOrderStatusUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(id: String, status: String): Result<Unit> {
        return repository.updateOrderStatus(id, status)
    }
}