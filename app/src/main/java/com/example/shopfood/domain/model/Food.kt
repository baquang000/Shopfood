package com.example.shopfood.domain.model

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val BestFood: Boolean = false,
    val CategoryId: Int = 0,
    val Description: String = "",
    val Price: Int = 0,
    val Star: Double = 0.0,
    val TimeValue: Int = 15,
    val Title: String = "",
    val show: Boolean = true,
    val Id: Int = 0,
    val ImagePath: String = "",
) : Parcelable

data class FoodWithFirebase(
    @PropertyName("bestFood")
    val bestFood: Boolean = false,

    @PropertyName("categoryId")
    val categoryId: Int = 0,

    @PropertyName("description")
    val description: String = "",

    @PropertyName("price")
    val price: Int = 0,

    @PropertyName("star")
    val star: Double = 0.0,

    @PropertyName("timeValue")
    val timeValue: Int = 15,

    @PropertyName("title")
    val title: String = "",

    @PropertyName("show")
    val show: Boolean = true,

    @PropertyName("id")
    val id: Int = 0,

    @PropertyName("imagePath")
    val imagePath: String = "",

    @PropertyName("stability")
    val stability: Int = 0
)

