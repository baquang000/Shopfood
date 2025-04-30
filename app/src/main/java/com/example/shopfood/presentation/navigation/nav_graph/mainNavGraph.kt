package com.example.shopfood.presentation.navigation.nav_graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.presentation.home.FoodDetailScreen
import com.example.shopfood.presentation.home.HomeScreen
import com.example.shopfood.presentation.home.SearchScreen
import com.example.shopfood.presentation.home.SeeAllRestaurantScreen
import com.example.shopfood.presentation.home.SeeAllScreen
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.presentation.navigation.Router
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Router.HomeScreen.route,
        route = Graph.MAIN
    ) {

        composable(Router.HomeScreen.route) { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            HomeScreen(
                homeViewModel = homeViewModel,
                onClickSeeAll = {
                    navController.navigate(Router.SeeAllScreen.route)
                },
                onClickSeeAllRestaurant = {
                    navController.navigate(Router.SeeAllRestaurantScreen.route)
                },
                onClickFood = { food, restaurant ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("food_detail", food)
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "restaurant",
                        restaurant
                    )
                    navController.navigate(Router.FoodDetailScreen.route)
                },
                onClickSearch = {
                    navController.navigate(Router.SearchScreen.route)
                }
            )
        }

        composable(Router.SearchScreen.route) { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            SearchScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.SeeAllScreen.route) { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            SeeAllScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.SeeAllRestaurantScreen.route) { backStackEntry ->
            val homeViewModel: HomeViewModel = hiltViewModel(backStackEntry)
            SeeAllRestaurantScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.FoodDetailScreen.route) {
            val food = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<FoodWithRestaurant>("food_detail")

            val restaurant = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Restaurant>("restaurant")

            DisposableEffect(Unit) {
                onDispose {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<FoodWithRestaurant>("food_detail")
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Restaurant>("restaurant")
                }
            }
            if (food != null && restaurant != null) {
                FoodDetailScreen(
                    food = food,
                    restaurant = restaurant,
                    onBackClick = {
                        navController.navigateUp()
                    })
            }
        }

    }
}
