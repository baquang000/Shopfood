package com.example.shopfood.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.FoodCardInCart
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.viewmodel.home.OrderViewModel

@Composable
fun CartScreen(
    orderViewModel: OrderViewModel,
    onBackClick: () -> Unit,
    onNavigateToSuccess : () -> Unit
) {
    val foodInCart = orderViewModel.selectedFoods
    val totalPrice = orderViewModel.totalPrice
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.background,
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                title = "Cart",
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                iconColor = MaterialTheme.colorScheme.surface,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            BottomBarWithCart(
                totalPrice = totalPrice,
                onClickPlaceOrder = {
                    orderViewModel.submitOrderToRealtimeDatabase(
                        onResult = { success, message ->
                            if (success) {
                                onNavigateToSuccess()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Đặt hàng thất bại: $message",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                    )
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 12.dp),
            ) {
                items(foodInCart) { food ->
                    FoodCardInCart(
                        modifier = Modifier.padding(vertical = 8.dp),
                        food = food,
                        onClick = {},
                        onClickAdd = {
                            orderViewModel.toggleSelectFood(food.foodWithRestaurant)
                        },
                        onClickSub = {
                            orderViewModel.decreaseFoodQuantity(food.foodWithRestaurant)
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    )
}

@Composable
fun BottomBarWithCart(
    modifier: Modifier = Modifier,
    totalPrice: Int = 0,
    onClickPlaceOrder: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(top = 12.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextCustom(
                    text = R.string.delivery_adr,
                    color = Color.Gray.copy(0.8f)
                )
                TextCustom(
                    text = R.string.edit,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.8f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "ADddres asdahsduhasuidasiud",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextCustom(
                    text = R.string.total,
                    color = Color.Gray.copy(0.8f)
                )
                TextCustomInputText(
                    text = "$ $totalPrice",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.scrim
                )
            }
            ButtonCustom(
                text = R.string.place_order,
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickPlaceOrder
            )
        }
    }
}