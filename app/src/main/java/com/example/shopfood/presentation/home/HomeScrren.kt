package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.presentation.component.CategoryCard
import com.example.shopfood.presentation.component.FoodCard
import com.example.shopfood.presentation.component.RestaurantCard
import com.example.shopfood.presentation.component.ScaffoldWithNoSafeArea
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.component.TextFieldCustomWithSearch
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel
import com.example.shopfood.presentation.viewmodel.home.OrderViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme
import com.example.shopfood.ui.theme.grayCustomLight
import com.example.shopfood.ui.theme.textColorGrayLight

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    orderViewModel: OrderViewModel,
    onClickSeeAll: () -> Unit = {},
    onClickSearch: () -> Unit = {},
    onClickSeeAllRestaurant: () -> Unit = {},
    onClickCart: () -> Unit,
    onClickFood: (FoodWithRestaurant, Restaurant) -> Unit = { _, _ -> },
    onClickRestaurant: (Restaurant) -> Unit = {},
    onClickToProfile: () -> Unit = {}
) {
    val restaurantState by homeViewModel.restaurantState.collectAsStateWithLifecycle()
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    val restaurantMap = remember(restaurantState) {
        (restaurantState as? RestaurantState.Success)
            ?.restaurantList
            ?.associateBy { it.Id }
            ?: emptyMap()
    }
    val numberFoodInCart = orderViewModel.totalQuantity

    ScaffoldWithNoSafeArea { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp), contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                SectionHomeFirst(
                    numberFoodInCart = numberFoodInCart,
                    onClickCart = onClickCart,
                    onClickToProfile = onClickToProfile
                )
            }
            item {
                TextFieldCustomWithSearch(
                    value = "",
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            onClickSearch()
                        }
                )
            }
            item {
                SectionHomeWithCategory(
                    onClickSeeAll = onClickSeeAll
                )
            }
            if (selectedCategoryId == null) {
                item {
                    ListCategory(
                        onCategoryClick = { category ->
                            selectedCategoryId = category.id
                            homeViewModel.filterFoodsByCategory(category.id)
                        }
                    )
                }
            } else {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(homeViewModel.filteredFoods) { food ->
                            val restaurant =
                                restaurantMap[food.restaurantId.toInt()] ?: Restaurant()
                            FoodCard(
                                food = food,
                                onClick = {
                                    onClickFood(food, restaurant)
                                },
                                onClickAdd = {
                                    orderViewModel.toggleSelectFood(food)
                                }
                            )
                        }
                    }
                }
            }
            item {
                SectionRestaurant(
                    restaurantState = restaurantState,
                    onClickSeeAllRestaurant = onClickSeeAllRestaurant,
                    onClick = { restaurant ->
                        onClickRestaurant(restaurant)
                    }
                )
            }
        }
    }
}

@Composable
fun SectionHomeFirst(
    modifier: Modifier = Modifier,
    onClickCart: () -> Unit,
    numberFoodInCart: Int,
    onClickToProfile: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(48.dp)
                        .background(
                            color = grayCustomLight, shape = CircleShape
                        )
                        .clickable{
                            onClickToProfile()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Apps, "", tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
                ) {
                    TextCustom(
                        text = R.string.Delivery_to,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row {
                        Text(
                            "40 Nguyễn Văn Linh, Quận 7, TP.HCM",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            maxLines = 1,
                            modifier = Modifier.width(150.dp),
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            "",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
            CardWithNumber(
                numberFoodInCart = numberFoodInCart,
                onClickCart = onClickCart
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            TextCustomInputText(
                text = "Hey Halal,",
                color = textColorGrayLight,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Normal
                ),
            )
            TextCustomInputText(
                text = "Good Afternoon",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Composable
fun SectionHomeWithCategory(
    modifier: Modifier = Modifier,
    onClickSeeAll: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustom(
                text = R.string.All_Category, style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ), color = textColorGrayLight
            )
            Row(
                modifier = Modifier.clickable {
                    onClickSeeAll()
                }
            ) {
                TextCustom(
                    text = R.string.See_all, style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ), color = textColorGrayLight
                )
                Icon(
                    Icons.Default.ChevronRight, "", tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ListCategory(onCategoryClick: (Category) -> Unit = {}) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(CategoryList) { category ->
            CategoryCard(
                category = category, onClick = {
                    onCategoryClick(category)
                })
        }
    }
}

@Composable
fun SectionRestaurant(
    modifier: Modifier = Modifier, restaurantState: RestaurantState,
    onClickSeeAllRestaurant: () -> Unit = {},
    onClick: (Restaurant) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustom(
                text = R.string.open_restaurant
            )
            Row(
                modifier = Modifier.clickable {
                    onClickSeeAllRestaurant()
                }
            ) {
                TextCustom(
                    text = R.string.See_all
                )
                Icon(
                    Icons.Default.ChevronRight, "", tint = MaterialTheme.colorScheme.outline
                )
            }
        }
        SetRestaurantItems(
            restaurantState = restaurantState,
            onClick = { restaurant ->
                onClick(restaurant)
            }
        )
    }
}

@Composable
fun CardWithNumber(
    modifier: Modifier = Modifier,
    onClickCart: () -> Unit,
    numberFoodInCart: Int
) {
    Box(
        modifier = modifier
            .padding(start = 36.dp)
            .size(48.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer, shape = CircleShape
            )
            .clickable {
                onClickCart()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingBag,
            contentDescription = "Shopping",
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 4.dp, y = (-4).dp)
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = numberFoodInCart.toString(),
                color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SetRestaurantItems(
    restaurantState: RestaurantState, modifier: Modifier = Modifier,
    onClick: (Restaurant) -> Unit = {}
) {
    when (restaurantState) {
        is RestaurantState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RestaurantState.Success -> {
            ListItemRestaurant(
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
fun ListItemRestaurant(
    modifier: Modifier, restaurant: List<Restaurant>,
    onClick: (Restaurant) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        restaurant.take(3).forEach { restaurant ->
            RestaurantCard(
                restaurant = restaurant, onClick = {
                    onClick(restaurant)
                })
        }
    }
}

data class Category(
    val imageRes: Int,
    val title: Int,
    val id: Int
)

val CategoryList = listOf(
    Category(
        imageRes = R.drawable.pizza,
        title = R.string.pizza,
        id = 0
    ),
    Category(
        imageRes = R.drawable.burger,
        title = R.string.burger,
        id = 1
    ),
    Category(
        imageRes = R.drawable.chicken,
        title = R.string.chicken,
        id = 2
    ),
    Category(
        imageRes = R.drawable.sushi,
        title = R.string.sushi,
        id = 3
    ),
    Category(
        imageRes = R.drawable.steak,
        title = R.string.steak,
        id = 4
    ),
    Category(
        imageRes = R.drawable.hot_dog,
        title = R.string.hot_dog,
        id = 5
    ),
    Category(
        imageRes = R.drawable.drink,
        title = R.string.drink,
        id = 6
    ),
    Category(
        imageRes = R.drawable.flame,
        title = R.string.all,
        id = 7
    ),
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShopfoodTheme {
        //HomeScreen()
    }
}