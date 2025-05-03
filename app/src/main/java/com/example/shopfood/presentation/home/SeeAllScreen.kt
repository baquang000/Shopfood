package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.CustomSnackBar
import com.example.shopfood.presentation.component.FoodSimpleCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.presentation.viewmodel.home.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun SeeAllScreen(
    homeViewModel: HomeViewModel,
    orderViewModel: OrderViewModel,
    onBackClick: () -> Unit = {},
    onClickCart: () -> Unit,
    onClickFood: (FoodWithRestaurant, Restaurant) -> Unit = { _, _ -> },
    onClickRestaurant: (Restaurant) -> Unit,
    onClickSeeAllRestaurant: () -> Unit
) {
    val foodState by homeViewModel.foodState.collectAsStateWithLifecycle()
    val restaurantState by homeViewModel.restaurantState.collectAsStateWithLifecycle()
    val restaurantMap = remember(restaurantState) {
        (restaurantState as? RestaurantState.Success)?.restaurantList?.associateBy { it.Id.toString() }
            ?: emptyMap()
    }
    val canLoadMore by homeViewModel.canLoadMore.collectAsStateWithLifecycle()
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
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                title = "See All",
                textStyle = MaterialTheme.typography.titleLarge,
                onBackClick = onBackClick
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
                    ShowFoodWithGrid(
                        foodState = foodState,
                        restaurantMap = restaurantMap,
                        onClick = { food, restaurant ->
                            onClickFood(food, restaurant)
                        },
                        onClickAdd = {
                            orderViewModel.toggleSelectFood(it)
                        }
                    )
                }
                if (canLoadMore) {
                    item {
                        ShowNextButton(
                            onClick = homeViewModel::loadMoreFoods
                        )
                    }
                } else {
                    item {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, end = 8.dp, start = 8.dp)
                        )
                    }
                }
                item {
                    SectionRestaurant(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                        restaurantState = restaurantState,
                        onClick = { restaurant ->
                            onClickRestaurant(restaurant)
                        },
                        onClickSeeAllRestaurant = onClickSeeAllRestaurant
                    )
                }
            }
        }
    )
}

@Composable
fun ShowFoodWithGrid(
    foodState: FoodState,
    restaurantMap: Map<String, Restaurant>,
    onClick: (FoodWithRestaurant, Restaurant) -> Unit = { _, _ -> },
    onClickAdd: (FoodWithRestaurant) -> Unit = {}
) {
    when (foodState) {
        is FoodState.Success -> {
            FoodGrid(
                foodState.foodList, restaurantMap,
                onClick = { food, restaurant ->
                    onClick(food, restaurant)
                },
                onClickAdd = {
                    onClickAdd(it)
                })
        }

        is FoodState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FoodState.Failure -> Text("Error: ${foodState.error}")
        FoodState.Empty -> Text("No food found")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodGrid(
    foods: List<FoodWithRestaurant>,
    restaurantMap: Map<String, Restaurant>,
    onClick: (FoodWithRestaurant, Restaurant) -> Unit = { _, _ -> },
    onClickAdd: (FoodWithRestaurant) -> Unit = {}
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2
    ) {
        foods.forEach { foodWithRestaurant ->
            val restaurant = restaurantMap[foodWithRestaurant.restaurantId] ?: Restaurant()
            FoodSimpleCard(
                food = foodWithRestaurant.food,
                restaurantName = restaurant.Name,
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2),
                onClick = {
                    onClick(foodWithRestaurant, restaurant)
                },
                onClickAdd = {
                    onClickAdd(foodWithRestaurant)
                }
            )
        }
    }
}

@Composable
fun ShowNextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        ButtonCustom(
            modifier = Modifier
                .weight(1.5f)
                .padding(horizontal = 8.dp),
            text = R.string.show_more,
            enabled = true,
            cornerRadius = 24.dp,
            onClick = onClick
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}
