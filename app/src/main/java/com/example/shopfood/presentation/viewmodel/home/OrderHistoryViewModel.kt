package com.example.shopfood.presentation.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.OrderWithFirebase
import com.example.shopfood.domain.usecase.firebase.home.order.OrderUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases,
    private val auth: FirebaseAuth
) : ViewModel() {
    var orders by mutableStateOf<List<OrderWithFirebase>>(emptyList())
        private set

    init {
        loadOrders()
    }

    fun loadOrders() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = orderUseCases.getOrdersByUserId(userId)
            if (result.isSuccess) {
                orders = result.getOrDefault(emptyList())
            }
        }
    }

    fun cancelOrder(id: String) {
        viewModelScope.launch {
            orderUseCases.updateOrderStatus(id, "Cancelled")
            loadOrders()
        }
    }

    fun completeOrder(id: String) {
        viewModelScope.launch {
            orderUseCases.updateOrderStatus(id, "Completed")
            loadOrders()
        }
    }

}