package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.RestaurantState
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getAllRestaurant(): Flow<RestaurantState>
}