package com.example.shopfood.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.shopfood.R
import com.example.shopfood.domain.model.Food
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.presentation.home.Category
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(end = 8.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable(
                    onClick = onClick
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
            TextCustom(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = category.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CategorySimpleCard(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .padding(end = 8.dp)
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable(
                    onClick = onClick
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextCustom(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = category.title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun RestaurantCard(
    modifier: Modifier = Modifier,
    restaurant: Restaurant,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = restaurant.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            error = painterResource(id = R.drawable.error)
        )
        TextCustomInputText(
            text = restaurant.Name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 4.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Start,
        )
        TextCustomInputText(
            text = restaurant.category,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 12.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W700
            ),
            color = Color.Gray.copy(0.8f),
            textAlign = TextAlign.Start,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
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
                modifier = Modifier.padding(end = 36.dp)
            )
            IconWithText(
                text = restaurant.timeDelivery.toString()
            )
        }
    }
}

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.StarOutline,
    tintColor: Color = Color.Red,
    text: String = "",
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Gray.copy(0.8f)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            "",
            tint = tintColor,
            modifier = Modifier.padding(end = 4.dp)
        )
        TextCustomInputText(
            text = text,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = fontWeight
            ),
            color = textColor,
        )
    }
}

@Composable
fun CardRecentKeyWord(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = modifier
    ) {
        TextCustomInputText(
            text = "Burger",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun FoodSimpleCard(
    modifier: Modifier = Modifier,
    food: Food,
    restaurantName: String,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .width(160.dp)
            .height(240.dp)
            .clickable { }
    ) {
        AsyncImage(
            model = food.ImagePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            error = painterResource(id = R.drawable.error)
        )
        TextCustomInputText(
            text = food.Title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Start,
        )
        TextCustomInputText(
            text = restaurantName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 12.dp, end = 4.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W600
            ),
            color = Color.LightGray,
            textAlign = TextAlign.Start,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustomInputText(
                text = food.Price.toString() + "$",
                modifier = Modifier
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = MaterialTheme.colorScheme.scrim,
            )
            ButtonWithIconAdd(
                modifier = Modifier
            )
        }
    }
}

@Composable
fun FoodCard(
    modifier: Modifier = Modifier,
    food: FoodWithRestaurant,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .width(160.dp)
            .height(200.dp)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = food.food.ImagePath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            error = painterResource(id = R.drawable.error)
        )
        TextCustomInputText(
            text = food.food.Title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Start,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustomInputText(
                text = food.food.Price.toString() + "$",
                modifier = Modifier
                    .padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = MaterialTheme.colorScheme.scrim,
            )
            ButtonWithIconAdd(
                modifier = Modifier
            )
        }
    }
}

@Composable
fun SimpleRestaurantCard(
    modifier: Modifier = Modifier,
    restaurant: Restaurant,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = restaurant.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
                error = painterResource(id = R.drawable.error)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TextCustomInputText(
                    text = restaurant.Name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                )
                IconWithText(
                    icon = Icons.Filled.StarOutline,
                    tintColor = MaterialTheme.colorScheme.primary,
                    text = restaurant.star.toString()
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun CategoryCardPreview() {
    ShopfoodTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center
        ) {
            Row {
//                FoodSimpleCard()
//                FoodSimpleCard()
            }

        }

    }
}