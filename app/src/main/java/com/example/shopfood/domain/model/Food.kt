package com.example.shopfood.domain.model

data class Food(
    val BestFood: Boolean = false,
    val CategoryId: Int = 0,
    val Description: String = "",
    val Price: Int = 0,
    val Star: Double = 0.0,
    val TimeValue: Int = 15,
    val Title: String = "",
    val show: Boolean = true,
    val Id: Int = 0,
    val ImagePath: String = "",
)