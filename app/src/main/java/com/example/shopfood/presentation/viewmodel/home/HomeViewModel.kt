package com.example.shopfood.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.domain.usecase.firebase.home.restaurant.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
) : ViewModel() {
    private val _restaurantState = MutableStateFlow<RestaurantState>(RestaurantState.Empty)
    val restaurantState: StateFlow<RestaurantState> = _restaurantState

    init {
        fetchRestaurant()
    }

    private fun fetchRestaurant() {
        viewModelScope.launch {
            restaurantUseCases.getAllRestaurant().collect { restaurantState ->
                _restaurantState.value = restaurantState
            }

        }
    }
}