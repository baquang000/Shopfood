package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopfood.R
import com.example.shopfood.presentation.component.CardRecentKeyWord
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextFieldCustomWithSearch
import com.example.shopfood.presentation.component.TopBarWithTextAndTwoIcons
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun SearchScreen() {
    var valueSearch by remember { mutableStateOf("") }
    ScaffoldWithIconInTopBar(
        topBar = {
            TopBarWithTextAndTwoIcons(
                title = "Search",
                onBackClick = {},
                secondIcon = {
                    CardWithNumber()
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        color = MaterialTheme.colorScheme.surface
                    )
            ) {
                item {
                    TextFieldCustomWithSearch(
                        value = valueSearch,
                        onValueChange = { valueSearch = it },
                        modifier = Modifier
                    )
                }
                item {
                    SectionRecentKey()
                }
            }
        }
    )
}

@Composable
fun SectionRecentKey(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TextCustom(
            text = R.string.Recent_keywords
        )
        LazyRow {
            item {
                CardRecentKeyWord()
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun SearchScreenPreview() {
    ShopfoodTheme {
        SearchScreen()
    }
}