package com.example.shopfood.domain.model

import com.google.firebase.database.PropertyName

data class FoodInCard(
    val foodWithRestaurant : FoodWithRestaurant = FoodWithRestaurant(),
    val quantity : Int = 0
)

data class FoodInCardWithFirebase(
    @PropertyName("foodWithRestaurant")
    val foodWithRestaurant: FoodWithRestaurantWithFirebase = FoodWithRestaurantWithFirebase(),

    @PropertyName("quantity")
    val quantity: Int = 0
)