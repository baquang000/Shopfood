package com.example.shopfood.presentation.navigation

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

sealed class Router(val route: String){
    data object IntroductionScreen: Router("introduction_screen")
    data object SignInScreen: Router("sign_in_screen")
    data object LoginScreen: Router("login_screen")
    data object HomeScreen: Router("home_screen")
    data object SearchScreen: Router("search_screen")
    data object SeeAllScreen : Router("see_all_screen")
    data object SeeAllRestaurantScreen: Router("see_all_restaurant_screen")
    data object FoodDetailScreen : Router("food_detail_screen")
    data object RestaurantViewScreen : Router("restaurant_view_screen")
}