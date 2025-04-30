package com.example.shopfood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodWithRestaurant(
    val food: Food,
    val restaurantId: String
) : Parcelable
