package com.example.shopfood.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSnackBar(
    countFood: Int,
    price: Int,
    onAction: () -> Unit
) {
    Snackbar(containerColor = Color.White) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeadingImageWithCount(countFood)
            ContentSnackBarInShop(price = price) {
                onAction.invoke()
            }
        }

    }
}

@Composable
fun LeadingImageWithCount(
    count: Int,
) {
    Row(
        modifier = Modifier.wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = null,
            tint = Color.Red
        )
        if (count > 0) {
            Text(
                text = count.toString(),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ContentSnackBarInShop(
    price: Int,
    onAction: () -> Unit
) {
    Row(
        modifier = Modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$ $price", fontSize = 14.sp,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Button(
            onClick = {
                onAction.invoke()
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Order", fontSize = 12.sp, color = Color.White)
        }
    }
}


