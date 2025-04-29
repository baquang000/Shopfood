package com.example.shopfood.domain.usecase.firebase.home.food

import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.repository.firebase.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetAllFoodUseCase(
    private val repository: FoodRepository
) {
    operator fun invoke(): Flow<FoodState> = repository.getAllFood()
}