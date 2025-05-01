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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.presentation.component.FoodCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.component.TopBarWithTextAndTwoIcons
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchResultScreen(
    onBackClick: () -> Unit,
    valueSearch: String,
    homeViewModel: HomeViewModel
) {

    val filteredFoods = homeViewModel.filteredFoods

    val foodState by homeViewModel.foodState.collectAsState()

    LaunchedEffect(valueSearch, foodState) {
        if (foodState is FoodState.Success) {
            homeViewModel.filterFoodsByQuery(valueSearch)
        }
    }

    ScaffoldWithIconInTopBar(
        topBar = {
            TopBarWithTextAndTwoIcons(
                title = "Search",
                onBackClick = onBackClick,
                secondIcon = {
                    CardWithNumber()
                }
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
                                FoodCard(
                                    modifier = Modifier,
                                    food = foodWithRestaurant,
                                    onClick = { /* handle click */ }
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No food found for \"$valueSearch\"",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                item{
                    TextCustomInputText(
                        text = "Restaurant",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp)
                    )
                }
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