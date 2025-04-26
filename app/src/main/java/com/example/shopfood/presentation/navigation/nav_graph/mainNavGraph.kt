package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopfood.presentation.home.HomeScreen
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.presentation.navigation.Router
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Router.HomeScreen.route,
        route = Graph.MAIN
    ) {
        composable(Router.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
            )
        }
    }
}
