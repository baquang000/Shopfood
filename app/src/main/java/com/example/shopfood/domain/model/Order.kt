package com.example.shopfood.domain.model


data class Order(
    val userId: String = "",
    val items: List<FoodInCard> = emptyList(),
    val totalPrice: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
