package com.example.shopfood.presentation.viewmodel.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.FoodInCard
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Order
import com.example.shopfood.domain.usecase.firebase.home.order.OrderUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCase: OrderUseCases,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _selectedFoods = mutableStateListOf<FoodInCard>()
    val selectedFoods: List<FoodInCard> get() = _selectedFoods

    val totalQuantity: Int
        get() = _selectedFoods.sumOf { it.quantity }

    val totalPrice: Int
        get() = _selectedFoods.sumOf { it.foodWithRestaurant.food.Price * it.quantity }

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

    fun submitOrderToRealtimeDatabase(
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = firebaseAuth.currentUser
        val userId = user?.uid

        if (userId == null) {
            onResult(false, "User not logged in")
            return
        }

        val order = Order(
            userId = userId,
            items = selectedFoods,
            totalPrice = totalPrice,
            timestamp = System.currentTimeMillis(),
            status = "Ongoing"
        )


        viewModelScope.launch {
            val result = orderUseCase.saveOrder(order)
            if (result.isSuccess) {
                onResult(true, null)
                _selectedFoods.clear()
            } else {
                onResult(false, result.exceptionOrNull()?.message)
                Log.e("OrderViewModel", "Error saving order: ${result.exceptionOrNull()?.message}")
            }
        }
    }

}
