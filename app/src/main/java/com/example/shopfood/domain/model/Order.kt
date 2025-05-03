package com.example.shopfood.domain.model

import com.google.firebase.database.PropertyName


data class Order(
    val userId: String = "",
    val items: List<FoodInCard> = emptyList(),
    val totalPrice: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Ongoing"
)

data class OrderWithFirebase(
    val id: String = "",
    @PropertyName("userId") val userId: String = "",
    @PropertyName("items") val items: List<FoodInCardWithFirebase> = emptyList(),
    @PropertyName("totalPrice") val totalPrice: Int = 0,
    @PropertyName("timestamp") val timestamp: Long = 0,
    @PropertyName("status") val status: String = "Ongoing"
)