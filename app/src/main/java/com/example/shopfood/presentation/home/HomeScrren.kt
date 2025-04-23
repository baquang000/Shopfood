package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.CategoryCard
import com.example.shopfood.presentation.component.RestaurantCard
import com.example.shopfood.presentation.component.ScaffoldWithNoSafeArea
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.component.TextFieldCustomWithSearch
import com.example.shopfood.ui.theme.ShopfoodTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun HomeScreen() {
    var valueSearch by remember { mutableStateOf("") }
    ScaffoldWithNoSafeArea { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            item {
                SectionHomeFirst()
            }
            item {
                TextFieldCustomWithSearch(
                    value = valueSearch,
                    onValueChange = { valueSearch = it },
                    modifier = Modifier
                )
            }
            item {
                SectionHomeWithCategory()
            }
            item {
                ListCategory()
            }
            item {
                SectionRestaurant()
            }
        }
    }
}

@Composable
fun SectionHomeFirst(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Apps, "")
            }
            Column {
                TextCustom(
                    text = R.string.Delivery_to,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    Text(
                        "40 Nguyễn Văn Linh, Quận 7, TP.HCM",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1,
                        modifier = Modifier.width(200.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        "",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            CardWithNumber()
        }
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            TextCustomInputText(
                text = "Hey Halal,",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextCustomInputText(
                text = "Good Afternoon",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Composable
fun SectionHomeWithCategory(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustom(
                text = R.string.All_Category
            )
            Row {
                TextCustom(
                    text = R.string.See_all
                )
                Icon(
                    Icons.Default.ChevronRight,
                    "",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ListCategory(onCategoryClick: (Category) -> Unit = {}) {
    val listState = rememberLazyListState()
    val selectedIndex = remember { mutableStateOf(0) }      // Để highlight
    val clickedIndex = remember { mutableStateOf<Int?>(null) } // Xử lý riêng sau click
    val coroutineScope = rememberCoroutineScope()
    var cardSize by remember { mutableIntStateOf(0) }

    // Lắng nghe scroll tự động chọn item
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                if (visibleItems.isEmpty()) return@map selectedIndex.value

                val totalItems = layoutInfo.totalItemsCount
                val viewportStart = layoutInfo.viewportStartOffset
                val viewportEnd = viewportStart + layoutInfo.viewportSize.width

                val firstItem = visibleItems.first()
                val lastItem = visibleItems.last()

                // Nếu cuộn sát đầu
                if (listState.firstVisibleItemIndex == 0 && firstItem.offset >= -10) return@map 0

                // Nếu cuộn sát cuối
                if (lastItem.index == totalItems - 1 &&
                    (lastItem.offset + lastItem.size) <= (viewportEnd + 10)
                ) return@map totalItems - 1

                // Item gần giữa nhất
                val center = viewportStart + layoutInfo.viewportSize.width / 2
                visibleItems.minByOrNull {
                    abs((it.offset + it.size / 2) - center)
                }?.index ?: selectedIndex.value
            }
            .distinctUntilChanged()
            .collectLatest { index ->
                selectedIndex.value = index
            }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(CategoryList) { index, category ->
            val isSelected = selectedIndex.value == index

            CategoryCard(
                modifier = Modifier.onSizeChanged { cardSize = it.width },
                category = category,
                isSelected = isSelected,
                onClick = {
                    clickedIndex.value = index // <- đánh dấu đã click
                    coroutineScope.launch {
                        // Cuộn item ra giữa
                        listState.animateScrollToItem(
                            index = index,
                            scrollOffset = (listState.layoutInfo.viewportSize.width / 2) - (cardSize / 2)
                        )
                        delay(300) // chờ scroll xong chút xíu rồi gọi hàm click
                        onCategoryClick(category) // xử lý riêng
                    }
                }
            )
        }
    }
}

@Composable
fun SectionRestaurant(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextCustom(
                text = R.string.open_restaurant
            )
            Row {
                TextCustom(
                    text = R.string.See_all
                )
                Icon(
                    Icons.Default.ChevronRight,
                    "",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
        RestaurantCard {}
    }
}

@Composable
fun CardWithNumber(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 36.dp)
            .size(48.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingBag,
            contentDescription = "Shopping",
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 4.dp, y = (-4).dp)
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "3", // ví dụ có 3 sản phẩm
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class Category(
    val imageRes: Int,
    val title: Int,
)

val CategoryList = listOf(
    Category(
        imageRes = R.drawable.flame,
        title = R.string.all,
    ),
    Category(
        imageRes = R.drawable.burger,
        title = R.string.burger,
    ),
    Category(
        imageRes = R.drawable.drink,
        title = R.string.drink,
    ),
    Category(
        imageRes = R.drawable.hot_dog,
        title = R.string.hot_dog,
    ),
    Category(
        imageRes = R.drawable.pizza,
        title = R.string.pizza,
    ),
    Category(
        imageRes = R.drawable.steak,
        title = R.string.steak,
    ),
    Category(
        imageRes = R.drawable.sushi,
        title = R.string.sushi,
    )
)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ShopfoodTheme {
        HomeScreen()
    }
}