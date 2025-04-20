package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.shopfood.presentation.navigation.Graph

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavGraph(navController: NavHostController) {
    val timeAnimation = 1500

    NavHost(
        navController = navController,
        startDestination = Graph.AUTH,
        route = Graph.ROOT,
        enterTransition = {
            fadeIn(animationSpec = tween(timeAnimation)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(timeAnimation)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(timeAnimation)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(timeAnimation)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(timeAnimation)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(timeAnimation)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(timeAnimation)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(timeAnimation)
            )
        }
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}
