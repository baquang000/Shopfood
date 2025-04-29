package com.example.shopfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopfood.presentation.home.SearchScreen
import com.example.shopfood.presentation.home.SeeAllScreen
import com.example.shopfood.presentation.navigation.nav_graph.RootNavGraph
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShopfoodTheme {
//                var showSplash by remember { mutableStateOf(true) }
//
//                if (showSplash) {
//                    CustomSplashScreen {
//                        showSplash = false
//                    }
//                } else {
//                    AppNav()
//                }
                val homeViewModel: HomeViewModel = hiltViewModel()
                SearchScreen(homeViewModel)
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
