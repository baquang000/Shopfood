package com.example.shopfood.domain.model

data class Restaurant(
    val Id: Int = 0,
    val Name: String = "",
    val image: String = "",
    val category: String = "",
    val star: Double = 0.0,
    val delivery : String = "",
    val timeDelivery : Int = 0
)
