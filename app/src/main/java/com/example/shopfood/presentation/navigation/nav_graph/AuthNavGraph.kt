package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopfood.presentation.auth.IntroductionScreen
import com.example.shopfood.presentation.auth.LoginScreen
import com.example.shopfood.presentation.auth.SignInScreen
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.presentation.navigation.Router

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Router.IntroductionScreen.route,
        route = Graph.AUTH
    ) {
        composable(Router.IntroductionScreen.route) {
            IntroductionScreen(
                onSkip = {
                    navController.navigate(Router.LoginScreen.route)
                }
            )
        }

        composable(Router.SignInScreen.route) {
            SignInScreen(
                onLogin = {
                    navController.navigate(Router.LoginScreen.route)
                }
            )
        }

        composable(Router.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Graph.MAIN) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                },
                onSignUp = {
                    navController.navigate(Router.SignInScreen.route)
                }
            )
        }
    }
}
