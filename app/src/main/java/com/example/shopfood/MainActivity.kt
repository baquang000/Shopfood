package com.example.shopfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.shopfood.presentation.auth.IntroductionScreen
import com.example.shopfood.ui.theme.ShopfoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopfoodTheme {
                IntroductionScreen()
            }
        }
    }
}

