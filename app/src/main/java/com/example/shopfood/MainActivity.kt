package com.example.shopfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.shopfood.presentation.navigation.nav_graph.RootNavGraph
import com.example.shopfood.presentation.viewmodel.auth.StartUpViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    val viewModel: StartUpViewModel = hiltViewModel()
    val startDestination = viewModel.startDestination.value

    if (startDestination != null) {
        RootNavGraph(
            navController = navController,
            startDestination = startDestination
        )
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
