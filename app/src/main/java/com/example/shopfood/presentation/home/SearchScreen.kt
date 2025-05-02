package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.CardRecentKeyWord
import com.example.shopfood.presentation.component.FoodSimpleCard
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleRestaurantCard
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextFieldCustomWithSearch
import com.example.shopfood.presentation.component.TopBarWithTextAndTwoIcons
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun SearchScreen(
    homeViewModel: HomeViewModel,
    onBackClick: () -> Unit,
    onClickCart: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    var valueSearch by remember { mutableStateOf("") }
    val restaurantState by homeViewModel.restaurantState.collectAsStateWithLifecycle()
    val foodState by homeViewModel.foodState.collectAsStateWithLifecycle()
    val restaurantMap = remember(restaurantState) {
        (restaurantState as? RestaurantState.Success)?.restaurantList?.associateBy { it.Id.toString() }
            ?: emptyMap()
    }
    val bestFoods = remember(foodState) {
        homeViewModel.getBestFoods()
    }
    ScaffoldWithIconInTopBar(
        topBar = {
            TopBarWithTextAndTwoIcons(
                title = "Search",
                onBackClick = onBackClick,
                secondIcon = {
                    CardWithNumber(
                        numberFoodInCart = 1,
                        onClickCart = onClickCart
                    )
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
                    TextFieldCustomWithSearch(
                        value = valueSearch,
                        onValueChange = { valueSearch = it },
                        searchClick = {
                            onSearchClick(valueSearch)
                        },
                        clearClick = {
                            valueSearch = ""
                        },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
                item {
                    SectionRecentKey(
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 8.dp
                        )
                    )
                }
                item {
                    SectionRestaurantSuggested(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        restaurantState = restaurantState
                    )
                }
                item {
                    SectionPopularFood(bestFoods = bestFoods, restaurantMap = restaurantMap)
                }

            }
        }
    )
}

@Composable
fun SectionRecentKey(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextCustom(
            text = R.string.Recent_keywords,
        )
        LazyRow {
            item {
                CardRecentKeyWord(modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
fun SectionRestaurantSuggested(
    restaurantState: RestaurantState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TextCustom(
            text = R.string.Restaurant_suggested,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        SetRestaurantItemsInSearch(restaurantState)
    }
}

@Composable
fun SetRestaurantItemsInSearch(restaurantState: RestaurantState, modifier: Modifier = Modifier) {
    when (restaurantState) {
        is RestaurantState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RestaurantState.Success -> {
            ListItemRestaurantWithSimpleCard(
                modifier = modifier, restaurant = restaurantState.restaurantList
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
fun ListItemRestaurantWithSimpleCard(
    modifier: Modifier, restaurant: List<Restaurant>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        restaurant.take(5).forEach { item ->
            SimpleRestaurantCard(
                restaurant = item, onClick = {})
        }
    }
}

@Composable
fun SectionPopularFood(
    modifier: Modifier = Modifier,
    bestFoods: List<FoodWithRestaurant>,
    restaurantMap: Map<String, Restaurant>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        TextCustom(
            text = R.string.popular_food
        )
        ShowFoodWithBestFood(
            modifier = Modifier.padding(top = 8.dp),
            bestFoods, restaurantMap
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowFoodWithBestFood(
    modifier: Modifier = Modifier,
    foods: List<FoodWithRestaurant>,
    restaurantMap: Map<String, Restaurant>,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2
    ) {
        foods.take(5).forEach { foodWithRestaurant ->
            val restaurant = restaurantMap[foodWithRestaurant.restaurantId]
            FoodSimpleCard(
                food = foodWithRestaurant.food,
                restaurantName = restaurant?.Name ?: "Unknown",
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2),
                onClickAdd = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun SearchScreenPreview() {
    ShopfoodTheme {
        //SearchScreen()
    }
}