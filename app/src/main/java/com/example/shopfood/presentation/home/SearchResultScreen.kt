package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.CustomSnackBar
import com.example.shopfood.presentation.component.FoodCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.component.TopBarWithTextAndTwoIcons
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.presentation.viewmodel.home.OrderViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchResultScreen(
    onBackClick: () -> Unit,
    onClickCart: () -> Unit,
    valueSearch: String,
    homeViewModel: HomeViewModel,
    orderViewModel: OrderViewModel,
    onClickFood: (FoodWithRestaurant, Restaurant) -> Unit = { _, _ -> },
) {
    val restaurantState by homeViewModel.restaurantState.collectAsStateWithLifecycle()
    val restaurantMap = remember(restaurantState) {
        (restaurantState as? RestaurantState.Success)?.restaurantList?.associateBy { it.Id.toString() }
            ?: emptyMap()
    }
    val filteredFoods = homeViewModel.filteredFoods

    val foodState by homeViewModel.foodState.collectAsState()

    LaunchedEffect(valueSearch, foodState) {
        if (foodState is FoodState.Success) {
            homeViewModel.filterFoodsByQuery(valueSearch)
        }
    }

    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(orderViewModel.totalQuantity) {
        if (orderViewModel.totalQuantity > 0) {
            coroutineScope.launch {
                snackState.showSnackbar("", duration = SnackbarDuration.Indefinite)
            }
        } else {
            snackState.currentSnackbarData?.dismiss()
        }
    }

    ScaffoldWithIconInTopBar(
        topBar = {
            TopBarWithTextAndTwoIcons(
                title = "Search",
                onBackClick = onBackClick,
                secondIcon = {}
            )
        },
        snackBarHost = {
            SnackbarHost(
                modifier = Modifier
                    .background(color = Color.White),
                hostState = snackState
            ) {
                CustomSnackBar(
                    countFood = orderViewModel.totalQuantity,
                    price = orderViewModel.totalPrice
                ) {
                    onClickCart()
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                item {
                    TextCustomInputText(
                        text = "Search Result: $valueSearch",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
                    )
                }
                item {
                    if (filteredFoods.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            maxItemsInEachRow = 2
                        ) {
                            filteredFoods.forEach { foodWithRestaurant ->
                                val restaurant = restaurantMap[foodWithRestaurant.restaurantId]
                                FoodCard(
                                    modifier = Modifier,
                                    food = foodWithRestaurant,
                                    onClick = {
                                        onClickFood(foodWithRestaurant, restaurant ?: Restaurant())
                                    },
                                    onClickAdd = {
                                        orderViewModel.toggleSelectFood(foodWithRestaurant)
                                    }
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No food found for \"$valueSearch\"",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.background
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

//                item{
//                    TextCustomInputText(
//                        text = "Restaurant",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
//                    )
//                }
//
//                item {
//                    if (filteredRestaurantsSearch.isNotEmpty()) {
//                        ListItemRestaurantWithSimpleCard(
//                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
//                            restaurant = filteredRestaurantsSearch
//                        )
//                    } else {
//                        Text(
//                            text = "No restaurant found for \"$valueSearch\"",
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                }

            }
        }
    )
}