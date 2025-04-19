package com.example.shopfood.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun ScaffoldWithNoSafeArea(
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        contentWindowInsets = WindowInsets(0.dp),
        content = content
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScaffoldWithNoSafeAreaPreview() {
    ShopfoodTheme {
        ScaffoldWithNoSafeArea(
            topBar = { TopAppBar(title = { Text("Hello") }) },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Text("No Safe Area!")
                }
            }
        )
    }
}