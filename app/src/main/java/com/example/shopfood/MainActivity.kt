package com.example.shopfood

import com.example.shopfood.presentation.navigation.nav_graph.RootNavGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shopfood.ui.theme.ShopfoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopfoodTheme {
                AppNav()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNav() {
    val navController = rememberNavController()
    RootNavGraph(navController)
}
