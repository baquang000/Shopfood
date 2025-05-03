package com.example.shopfood.domain.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodWithRestaurant(
    val food: Food = Food(),
    val restaurantId: String = ""
) : Parcelable


data class FoodWithRestaurantWithFirebase(
    @PropertyName("restaurantId")
    val restaurantId: String = "",

    @PropertyName("food")
    val food: FoodWithFirebase = FoodWithFirebase(),

    @PropertyName("stability")
    val stability: Int = 0
)