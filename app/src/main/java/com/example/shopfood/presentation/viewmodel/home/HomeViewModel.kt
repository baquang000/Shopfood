package com.example.shopfood.presentation.viewmodel.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.domain.usecase.firebase.home.food.FoodUseCases
import com.example.shopfood.domain.usecase.firebase.home.restaurant.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases,
    private val foodUseCases: FoodUseCases
) : ViewModel() {
    private val _restaurantState = MutableStateFlow<RestaurantState>(RestaurantState.Empty)
    val restaurantState: StateFlow<RestaurantState> = _restaurantState


    private val _foodState = MutableStateFlow<FoodState>(FoodState.Empty)
    val foodState: StateFlow<FoodState> = _foodState

    private val allFoods = mutableListOf<FoodWithRestaurant>()
    private var currentPage = 0
    private val pageSize = 10

    private val _canLoadMore = MutableStateFlow(true)
    val canLoadMore: StateFlow<Boolean> = _canLoadMore

    private val _filteredFoods = mutableStateListOf<FoodWithRestaurant>()
    val filteredFoods: List<FoodWithRestaurant> get() = _filteredFoods


    init {
        fetchRestaurant()
        fetchFoods()
    }

    private fun fetchRestaurant() {
        viewModelScope.launch {
            restaurantUseCases.getAllRestaurant().collect { restaurantState ->
                _restaurantState.value = restaurantState
            }

        }
    }

    private fun fetchFoods() {
        viewModelScope.launch {
            foodUseCases.getAllFood().collect { state ->
                when (state) {
                    is FoodState.Success -> {
                        allFoods.clear()
                        allFoods.addAll(state.foodList)
                        currentPage = 1
                        emitPagedFoods()
                    }

                    else -> {
                        _foodState.value = state
                    }
                }
            }
        }
    }

    private fun emitPagedFoods() {
        val toIndex = (currentPage * pageSize).coerceAtMost(allFoods.size)
        val pagedList = allFoods.subList(0, toIndex)
        _foodState.value = FoodState.Success(pagedList)
        _canLoadMore.value = (currentPage * pageSize) < allFoods.size
    }

    fun loadMoreFoods() {
        if ((currentPage * pageSize) < allFoods.size) {
            currentPage++
            emitPagedFoods()
        }
    }

    fun getBestFoods(): List<FoodWithRestaurant> {
        return allFoods.filter { it.food.BestFood }
    }

    fun filterFoodsByCategory(categoryId: Int) {
        _filteredFoods.clear()
        _filteredFoods.addAll(allFoods.filter { it.food.CategoryId == categoryId })
    }

    fun filterFoodsByRestaurantAndCategory(restaurantId: Int, categoryId: Int) {
        viewModelScope.launch {
            if (allFoods.isEmpty()) {
                foodUseCases.getAllFood().collect { state ->
                    if (state is FoodState.Success) {
                        allFoods.addAll(state.foodList)
                    }
                }
            }

            val results = allFoods.filter {
                it.food.CategoryId == categoryId &&
                        it.restaurantId == restaurantId.toString()
            }

            _filteredFoods.clear()
            _filteredFoods.addAll(results)
        }
    }

    fun filterFoodsByQuery(query: String) {
        viewModelScope.launch {
            if (allFoods.isEmpty()) {
                foodUseCases.getAllFood().collect { state ->
                    if (state is FoodState.Success) {
                        allFoods.addAll(state.foodList)
                    }
                }
            }
            val results = allFoods.filter {
                it.food.Title.contains(query, ignoreCase = true)
            }
            _filteredFoods.clear()
            _filteredFoods.addAll(results)
        }
    }

}