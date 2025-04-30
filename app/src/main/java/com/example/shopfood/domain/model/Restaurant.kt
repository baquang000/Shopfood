package com.example.shopfood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val Id: Int = 0,
    val Name: String = "",
    val image: String = "",
    val category: String = "",
    val star: Double = 0.0,
    val delivery : String = "",
    val timeDelivery : Int = 0
) : Parcelable
