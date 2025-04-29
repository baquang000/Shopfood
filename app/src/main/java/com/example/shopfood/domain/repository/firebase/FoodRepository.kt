package com.example.shopfood.domain.repository.firebase

import com.example.shopfood.domain.model.FoodState
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getAllFood(): Flow<FoodState>
}