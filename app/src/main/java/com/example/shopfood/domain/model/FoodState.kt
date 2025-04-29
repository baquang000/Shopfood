package com.example.shopfood.domain.model

sealed class FoodState {
    data class Success(val foodList: List<FoodWithRestaurant>) : FoodState()
    data class Failure(val error: String) : FoodState()
    object Loading : FoodState()
    object Empty : FoodState()
}