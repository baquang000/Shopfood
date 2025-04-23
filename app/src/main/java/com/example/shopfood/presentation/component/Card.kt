package com.example.shopfood.presentation.component

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopfood.presentation.home.Category
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(targetValue = if (isSelected) 1.05f else 1f)
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
            .padding(end = 8.dp)
            .width(if (isSelected) 155.dp else 150.dp) // tăng chiều rộng
            .height(if (isSelected) 65.dp else 60.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = if (isSelected) 12.dp.toPx() else 4.dp.toPx()
                shape = RoundedCornerShape(40.dp)
                clip = true
            }
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(if (isSelected) 48.dp else 36.dp) // tăng size
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(if (isSelected) 36.dp else 24.dp) // icon cũng lớn hơn
                )
            }
            TextCustom(
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
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = MaterialTheme.colorScheme.error)
        )
        TextCustomInputText(
            text = "Rose Garden Restaurant",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.scrim,
            textAlign = TextAlign.Start,
        )
        TextCustomInputText(
            text = "Burger-Chicken-Riche-Wings",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconWithText(
                text = "4.7"
            )
            IconWithText(
                text = "Free"
            )
            IconWithText(
                text = "20 min"
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
            RestaurantCard {
                println("click")
            }
        }

    }
}