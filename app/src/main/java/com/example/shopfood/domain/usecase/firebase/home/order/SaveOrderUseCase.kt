package com.example.shopfood.domain.usecase.firebase.home.order

import com.example.shopfood.domain.model.Order
import com.example.shopfood.domain.repository.firebase.OrderRepository

class SaveOrderUseCase(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(order: Order): Result<Unit> {
        return orderRepository.saveOrder(order)
    }
}