package com.example.shopfood.presentation.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.util.DebugLogger
import com.example.shopfood.R
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
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            "",
            tint = tintColor
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
fun CardRecentKeyWord() {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
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
            CardRecentKeyWord()
        }

    }
}