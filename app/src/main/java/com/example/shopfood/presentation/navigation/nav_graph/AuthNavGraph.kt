package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopfood.presentation.auth.IntroductionScreen
import com.example.shopfood.presentation.auth.LoginScreen
import com.example.shopfood.presentation.auth.SignUpScreen
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.presentation.navigation.Router
import com.example.shopfood.presentation.viewmodel.auth.LoginViewModel
import com.example.shopfood.presentation.viewmodel.auth.SignUpViewModel

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
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                onLogin = {
                    navController.navigate(Router.LoginScreen.route)
                }
            )
        }

        composable(Router.LoginScreen.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = loginViewModel,
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
