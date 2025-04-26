package com.example.shopfood.domain.usecase.firebase.home.restaurant

import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.domain.repository.firebase.RestaurantRepository
import kotlinx.coroutines.flow.Flow


class GetAllRestaurantUseCase(
    private val repository: RestaurantRepository
) {
    operator fun invoke(): Flow<RestaurantState> = repository.getAllRestaurant()
}