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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.FoodSimpleCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel

@Composable
fun SeeAllScreen(
    viewModel: HomeViewModel
) {
    val foodState by viewModel.foodState.collectAsStateWithLifecycle()
    val restaurantState by viewModel.restaurantState.collectAsStateWithLifecycle()
    val restaurantMap = remember(restaurantState) {
        (restaurantState as? RestaurantState.Success)?.restaurantList?.associateBy { it.Id.toString() }
            ?: emptyMap()
    }
    val canLoadMore by viewModel.canLoadMore.collectAsStateWithLifecycle()

    ScaffoldWithIconInTopBar(
        topBar = {
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                backIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                title = "See All",
                onBackClick = {}
            )
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
                    ShowFood(
                        foodState = foodState,
                        restaurantMap = restaurantMap
                    )
                }
                if (canLoadMore) {
                    item {
                        ShowNextButton(
                            onClick = viewModel::loadMoreFoods
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
                        restaurantState = restaurantState)
                }
            }
        }
    )
}

@Composable
fun ShowFood(
    foodState: FoodState,
    restaurantMap: Map<String, Restaurant>
) {
    when (foodState) {
        is FoodState.Success -> {
            FoodGrid(foodState.foodList, restaurantMap)
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
    restaurantMap: Map<String, Restaurant>
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
            val restaurant = restaurantMap[foodWithRestaurant.restaurantId]
            FoodSimpleCard(
                food = foodWithRestaurant.food,
                restaurantName = restaurant?.Name ?: "Unknown",
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2)
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
