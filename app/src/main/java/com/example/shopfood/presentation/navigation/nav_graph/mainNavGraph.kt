package com.example.shopfood.presentation.navigation.nav_graph

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.presentation.home.CartScreen
import com.example.shopfood.presentation.home.FoodDetailScreen
import com.example.shopfood.presentation.home.HomeScreen
import com.example.shopfood.presentation.home.ProfileScreen
import com.example.shopfood.presentation.home.RestaurantDetailScreen
import com.example.shopfood.presentation.home.SearchResultScreen
import com.example.shopfood.presentation.home.SearchScreen
import com.example.shopfood.presentation.home.SeeAllRestaurantScreen
import com.example.shopfood.presentation.home.SeeAllScreen
import com.example.shopfood.presentation.home.SuccessOrderScreen
import com.example.shopfood.presentation.home.profile.AddressScreen
import com.example.shopfood.presentation.home.profile.EditAddressScreen
import com.example.shopfood.presentation.home.profile.EditUserInfoScreen
import com.example.shopfood.presentation.home.profile.OrderScreen
import com.example.shopfood.presentation.home.profile.UserInfoScreen
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.presentation.navigation.Router
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.presentation.viewmodel.home.OrderHistoryViewModel
import com.example.shopfood.presentation.viewmodel.home.OrderViewModel
import com.example.shopfood.presentation.viewmodel.home.UserViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Router.HomeScreen.route,
        route = Graph.MAIN
    ) {
        composable(Router.HomeScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
            val orderViewModel: OrderViewModel = hiltViewModel(mainEntry)
            HomeScreen(
                homeViewModel = homeViewModel,
                orderViewModel = orderViewModel,
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
                },
                onClickRestaurant = { restaurant ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "restaurant",
                        restaurant
                    )
                    navController.navigate(Router.RestaurantViewScreen.route)
                },
                onClickCart = {
                    navController.navigate(Router.CartScreen.route)
                },
                onClickToProfile = {
                    navController.navigate(Router.ProfileScreen.route)
                }
            )
        }

        composable(Router.SearchScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
            SearchScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                onSearchClick = { valueSearch ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "search_value",
                        valueSearch
                    )
                    navController.navigate(Router.SearchResultScreen.route)
                },
                onClickCart = {
                    navController.navigate(Router.CartScreen.route)
                }
            )
        }

        composable(Router.SeeAllScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
            SeeAllScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.SeeAllRestaurantScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
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

        composable(Router.RestaurantViewScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
            val restaurant = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Restaurant>("restaurant")
            DisposableEffect(Unit) {
                onDispose {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Restaurant>("restaurant")
                }
            }
            if (restaurant != null) {
                RestaurantDetailScreen(
                    homeViewModel = homeViewModel,
                    restaurant = restaurant,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }

        composable(Router.SearchResultScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(mainEntry)
            val value = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("search_value")
            DisposableEffect(Unit) {
                onDispose {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("search_value")
                }
            }
            SearchResultScreen(
                homeViewModel = homeViewModel,
                valueSearch = value ?: "",
                onBackClick = {
                    navController.navigateUp()
                },
                onClickCart = {
                    navController.navigate(Router.CartScreen.route)
                }
            )
        }

        composable(Router.CartScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val orderViewModel: OrderViewModel = hiltViewModel(mainEntry)
            CartScreen(
                orderViewModel = orderViewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                onNavigateToSuccess = {
                    navController.navigate(Router.SuccessOrderScreen.route)
                }
            )
        }
        composable(Router.SuccessOrderScreen.route) { backStackEntry ->
            SuccessOrderScreen(
                onBackClick = {
                    navController.navigate(Router.HomeScreen.route)
                }
            )
        }
        composable(Router.ProfileScreen.route) { backStackEntry ->
            ProfileScreen(
                onClickUserInfo = {
                    navController.navigate(Router.UserInfoScreen.route)
                },
                onBackClick = {
                    navController.navigate(Router.HomeScreen.route)
                },
                onClickAddress = {
                    navController.navigate(Router.AddressUserScreen.route)
                },
                onClickOrder = {
                    navController.navigate(Router.MyOrderScreen.route)
                }
            )
        }

        composable(Router.UserInfoScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val userViewModel: UserViewModel = hiltViewModel(mainEntry)
            UserInfoScreen(
                userViewModel = userViewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                onClickEdit = {
                    navController.navigate(Router.EditUserInfoScreen.route)
                }
            )
        }

        composable(Router.EditUserInfoScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val userViewModel: UserViewModel = hiltViewModel(mainEntry)
            EditUserInfoScreen(
                userViewModel = userViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.AddressUserScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val userViewModel: UserViewModel = hiltViewModel(mainEntry)
            AddressScreen(
                userViewModel = userViewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                onClickEdit = {
                    navController.navigate(Router.EditAddressUserScreen.route)
                }
            )
        }

        composable(Router.EditAddressUserScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val userViewModel: UserViewModel = hiltViewModel(mainEntry)
            EditAddressScreen(
                userViewModel = userViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Router.MyOrderScreen.route) { backStackEntry ->
            val mainEntry = remember(navController) {
                navController.getBackStackEntry(Graph.MAIN)
            }
            val orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel(mainEntry)
            OrderScreen(
                orderHistoryViewModel = orderHistoryViewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}
