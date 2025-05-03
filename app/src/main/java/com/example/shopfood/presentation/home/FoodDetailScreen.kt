package com.example.shopfood.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.shopfood.R
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.presentation.component.IconWithText
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun FoodDetailScreen(
    food: FoodWithRestaurant,
    restaurant: Restaurant,
    onBackClick: () -> Unit = {}
) {
    ScaffoldWithIconInTopBar(
        topBar = {
            SimpleTopBarWithBackIcon(
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                title = "Detail",
                textStyle = MaterialTheme.typography.titleLarge,
                onBackClick = onBackClick
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
                        model = food.food.ImagePath,
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
                    SectionBodyDetail(food = food, restaurant = restaurant)
                }
            }
        },

        )
}

@Composable
fun SectionBodyDetail(
    modifier: Modifier = Modifier,
    food: FoodWithRestaurant,
    restaurant: Restaurant
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        TextCustomInputText(
            text = food.food.Title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            TextCustomInputText(
                text = restaurant.Name
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
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
        Text(
            text = food.food.Description,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextCustom(
                text = R.string.size,
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }
            ) {
                TextCustom(
                    text = R.string.size_10,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }
            ) {
                TextCustom(
                    text = R.string.size_14,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Button(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }
            ) {
                TextCustom(
                    text = R.string.size_16,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        SectionIngredients()
    }
}

@Composable
fun SectionIngredients() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextCustom(
            text = R.string.ingredients,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(color = Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.flour),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(color = Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.chicken_thigh),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(color = Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.garlic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(color = Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.pepper),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(color = Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.tomato),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    ShopfoodTheme {
        // FoodDetailScreen()
    }
}