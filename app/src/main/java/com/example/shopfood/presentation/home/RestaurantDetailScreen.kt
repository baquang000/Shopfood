package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.presentation.component.CategorySimpleCard
import com.example.shopfood.presentation.component.FoodCard
import com.example.shopfood.presentation.component.IconWithText
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.component.TopBarWithTextAndTwoIcons
import com.example.shopfood.presentation.viewmodel.home.HomeViewModel

@Composable
fun RestaurantDetailScreen(
    homeViewModel: HomeViewModel,
    restaurant: Restaurant,
    onBackClick: () -> Unit = {}
) {

    val initialCategory =
        remember(restaurant) {
            CategoryList.firstOrNull { it.id != 7 } ?: CategoryList.first()
        }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    val filteredFoods = homeViewModel.filteredFoods

    val foodState by homeViewModel.foodState.collectAsState()

    LaunchedEffect(selectedCategory, restaurant, foodState) {
        if (foodState is FoodState.Success) {
            homeViewModel.filterFoodsByRestaurantAndCategory(
                restaurantId = restaurant.Id,
                categoryId = selectedCategory.id
            )
        }
    }


    ScaffoldWithIconInTopBar(
        topBar = {
            TopBarWithTextAndTwoIcons(
                modifier = Modifier,
                title = "Restaurant View",
                onBackClick = onBackClick,
                secondIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSecondary)
                            .clickable {},
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
                    .padding(vertical = 12.dp, horizontal = 24.dp)
            ) {
                item {
                    AsyncImage(
                        model = restaurant.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(
                                shape = RoundedCornerShape(12.dp)
                            )
                    )
                }
                item {
                    SectionBodyRestaurantDetail(restaurant = restaurant)
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(CategoryList) { category ->
                            val isSelected = category.id == selectedCategory.id
                            if (category.id != 7) {
                                CategorySimpleCard(
                                    category = category,
                                    isSelected = isSelected,
                                    onClick = {
                                        selectedCategory = category
                                    })
                            }
                        }
                    }
                }
                item {
//                    when {
//                        filteredFoods.isEmpty() -> {
//                            Box(
//                                modifier = Modifier.fillMaxWidth(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator()
//                            }
//                        }
//
//                        else -> {
//
//                        }
//                    }
                    SectionFoodList(
                        category = selectedCategory,
                        foods = filteredFoods
                    )
                }

            }
        },
    )
}

@Composable
fun SectionBodyRestaurantDetail(
    modifier: Modifier = Modifier,
    restaurant: Restaurant
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp)
    ) {
        TextCustomInputText(
            text = restaurant.Name,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Text(
            text = restaurant.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.padding(top = 12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconWithText(
                text = restaurant.star.toString(),
                modifier = Modifier.padding(end = 36.dp),
                fontWeight = FontWeight.Bold
            )
            IconWithText(
                text = restaurant.delivery,
                icon = Icons.Filled.DeliveryDining,
                modifier = Modifier.padding(end = 36.dp)
            )
            IconWithText(
                text = restaurant.timeDelivery.toString(),
                icon = Icons.Filled.AccessTime
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SectionFoodList(
    modifier: Modifier = Modifier,
    category: Category,
    foods: List<FoodWithRestaurant>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextCustomInputText(
                text = stringResource(id = category.title),
                style = MaterialTheme.typography.titleLarge
            )
            TextCustomInputText(
                text = "(${foods.size})",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 2
        ) {
            foods.forEach { foodWithRestaurant ->
                FoodCard(
                    modifier = Modifier,
                    food = foodWithRestaurant,
                    onClick = {}
                )
            }
        }
    }
}