package com.example.shopfood.until

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUntil {
    fun formatDate(timestamp: Long): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(Date(timestamp))
    }
}