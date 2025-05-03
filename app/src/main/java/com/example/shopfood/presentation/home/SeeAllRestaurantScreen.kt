package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.RestaurantCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel

@Composable
fun SeeAllRestaurantScreen(
    homeViewModel: HomeViewModel,
    onBackClick: () -> Unit = {},
    onClickRestaurant: (Restaurant) -> Unit
) {

    val restaurantState by homeViewModel.restaurantState.collectAsStateWithLifecycle()

    ScaffoldWithIconInTopBar(
        topBar = {
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                title = "See All Restaurant",
                onBackClick = onBackClick,
                textStyle = MaterialTheme.typography.titleLarge,
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
                    SetRestaurant(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                        restaurantState = restaurantState,
                        onClick = { restaurant ->
                            onClickRestaurant(restaurant)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SetRestaurant(restaurantState: RestaurantState, modifier: Modifier = Modifier,
                  onClick: (Restaurant) -> Unit = {}) {
    when (restaurantState) {
        is RestaurantState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RestaurantState.Success -> {
            ListItemRestaurants(
                modifier = modifier, restaurant = restaurantState.restaurantList,
                onClick = { restaurant ->
                    onClick(restaurant)
                }
            )
        }

        is RestaurantState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = restaurantState.error, style = TextStyle(
                        fontSize = 20.sp,
                    )
                )
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.error_loading_data), style = TextStyle(
                        fontSize = 20.sp, color = Color.Red
                    )
                )
            }
        }
    }
}

@Composable
fun ListItemRestaurants(
    modifier: Modifier, restaurant: List<Restaurant>,
    onClick: (Restaurant) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        restaurant.forEach { item ->
            RestaurantCard(
                restaurant = item, onClick = {
                    onClick(item)
                })
        }
    }
}