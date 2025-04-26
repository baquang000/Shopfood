package com.example.shopfood.domain.model

sealed class RestaurantState {
    data class Success(val restaurantList: List<Restaurant>) : RestaurantState()
    data class Failure(val error: String) : RestaurantState()
    object Loading : RestaurantState()
    object Empty : RestaurantState()
}
