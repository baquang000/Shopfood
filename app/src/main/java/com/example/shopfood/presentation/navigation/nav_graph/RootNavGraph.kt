package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.shopfood.presentation.navigation.Graph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(navController: NavHostController) {
    val timeAnimation = 1000
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH,
        route = Graph.ROOT,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(timeAnimation)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(timeAnimation)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(timeAnimation)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(timeAnimation)
            )
        }
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}
