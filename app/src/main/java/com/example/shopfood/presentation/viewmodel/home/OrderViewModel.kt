package com.example.shopfood.presentation.viewmodel.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shopfood.domain.model.FoodInCard
import com.example.shopfood.domain.model.FoodWithRestaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {
    private val _selectedFoods = mutableStateListOf<FoodInCard>()
    val selectedFoods: List<FoodInCard> get() = _selectedFoods

    val totalQuantity: Int
        get() = _selectedFoods.sumOf { it.quantity }

    fun toggleSelectFood(food: FoodWithRestaurant) {
        val existingRestaurantId = _selectedFoods.firstOrNull()?.foodWithRestaurant?.restaurantId

        // Nếu món ăn từ nhà hàng khác, xóa danh sách hiện tại
        if (existingRestaurantId != null && food.restaurantId != existingRestaurantId) {
            _selectedFoods.clear()
        }

        val index = _selectedFoods.indexOfFirst {
            it.foodWithRestaurant.food.Title == food.food.Title
        }

        if (index != -1) {
            // Món đã có → tăng số lượng
            val existingFood = _selectedFoods[index]
            _selectedFoods[index] = existingFood.copy(quantity = existingFood.quantity + 1)
        } else {
            // Món mới → thêm
            _selectedFoods.add(
                FoodInCard(foodWithRestaurant = food, quantity = 1)
            )
        }
    }

    fun decreaseFoodQuantity(food: FoodWithRestaurant) {
        val index = _selectedFoods.indexOfFirst {
            it.foodWithRestaurant.food.Title == food.food.Title
        }

        if (index != -1) {
            val existingFood = _selectedFoods[index]
            if (existingFood.quantity > 1) {
                _selectedFoods[index] = existingFood.copy(quantity = existingFood.quantity - 1)
            } else {
                _selectedFoods.removeAt(index)
            }
        }
    }

}
