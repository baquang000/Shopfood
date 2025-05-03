package com.example.shopfood.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun ScaffoldWithNoSafeArea(
    topBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,

) {
    Scaffold(
        topBar = topBar,
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = {
            snackBarHost()
        },
        content = content
    )
}

@Composable
fun ScaffoldWithIconInTopBar(
    content: @Composable (PaddingValues) -> Unit,
    topBar: @Composable () -> Unit,
    snackBarHost: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            topBar()
        },
        snackbarHost = {
            snackBarHost()
        },
        contentWindowInsets = WindowInsets(0.dp),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopBarWithBackIcon(
    modifier: Modifier = Modifier,
    title: String = "",
    backgroundIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    iconColor: Color = MaterialTheme.colorScheme.background,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                title,
                style = textStyle
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, end = 24.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(backgroundIconColor)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithTextAndTwoIcons(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    secondIcon: @Composable () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSecondary)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            secondIcon()
        },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
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