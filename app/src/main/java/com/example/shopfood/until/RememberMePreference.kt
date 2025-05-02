package com.example.shopfood.until

import android.content.Context
import android.content.SharedPreferences

object RememberMePreference {

    private const val PREFERENCES_NAME = "settings"
    private const val REMEMBER_ME_KEY = "remember_me"

    // Lấy SharedPreferences từ Context
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    // Lưu trạng thái Remember Me vào SharedPreferences
    fun setRememberMe(context: Context, remember: Boolean) {
        val sharedPreferences = getSharedPreferences(context)
        with(sharedPreferences.edit()) {
            putBoolean(REMEMBER_ME_KEY, remember)
            apply()
        }
    }

    // Lấy trạng thái Remember Me từ SharedPreferences
    fun getRememberMe(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(REMEMBER_ME_KEY, false)
    }
}


