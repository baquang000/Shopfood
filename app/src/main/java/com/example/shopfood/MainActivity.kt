package com.example.shopfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.shopfood.presentation.navigation.nav_graph.RootNavGraph
import com.example.shopfood.presentation.viewmodel.auth.StartUpViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.splashCondition }
        }
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

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set


    init {
        viewModelScope.launch {
            delay(300)
            splashCondition = false
        }
    }
}