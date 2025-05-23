package com.example.shopfood.domain.usecase.firebase.home.order

data class OrderUseCases(
    val saveOrder: SaveOrderUseCase,
    val getOrdersByUserId: GetOrdersByUserIdUseCase,
    val updateOrderStatus: UpdateOrderStatusUseCase
)