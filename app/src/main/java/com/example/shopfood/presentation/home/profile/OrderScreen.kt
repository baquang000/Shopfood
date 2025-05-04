package com.example.shopfood.presentation.home.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.shopfood.R
import com.example.shopfood.domain.model.OrderWithFirebase
import com.example.shopfood.domain.model.TabItem
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.viewmodel.home.OrderHistoryViewModel
import com.example.shopfood.until.DateUntil.formatDate
import kotlinx.coroutines.launch

val tabItem = listOf(
    TabItem("Ongoing"),
    TabItem("History"),
)

@Composable
fun OrderScreen(
    orderHistoryViewModel: OrderHistoryViewModel,
    onBackClick: () -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { tabItem.size },
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()
    val orders = orderHistoryViewModel.orders
    Scaffold(
        topBar = {
            TopBarOrder(
                onBackClick = onBackClick,
                onClickMore = {},
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabItem.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = tabItem.title, color = if (pagerState.currentPage == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                }
                            )
                        },
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier, state = pagerState
            ) { index ->
                when (index) {
                    0 -> OngoingOrder(
                        orders = orders.filter { it.status == "Ongoing" },
                        onClickCancel = { order ->
                            orderHistoryViewModel.cancelOrder(order.id)
                        },
                        onClickConfirm = { order ->
                            orderHistoryViewModel.completeOrder(order.id)
                        }
                    )

                    1 -> HistoryOrder(orders = orders.filter { it.status != "Ongoing" })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarOrder(
    onBackClick: () -> Unit, onClickMore: () -> Unit
) {
    TopAppBar(title = {
        TextCustom(
            text = R.string.my_order
        )
    }, navigationIcon = {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, end = 24.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f))
                .clickable(onClick = onBackClick), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(20.dp)
            )
        }
    }, actions = {
        Box(
            modifier = Modifier
                .padding(start = 12.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary)
                .clickable {
                    onClickMore()
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.MoreHoriz,
                contentDescription = "More",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(24.dp)
            )
        }
    })
}

@Composable
fun OngoingOrder(
    modifier: Modifier = Modifier,
    orders: List<OrderWithFirebase>,
    onClickConfirm: (OrderWithFirebase) -> Unit = {},
    onClickCancel: (OrderWithFirebase) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        items(orders) { order ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextCustomInputText(
                        text = "$${order.totalPrice}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = MaterialTheme.colorScheme.background
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(2.dp)
                            .height(18.dp)
                            .background(color = Color.LightGray.copy())
                    )
                    TextCustomInputText(
                        text = order.items.size.toString() + " item",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextCustomInputText(
                        text = order.status,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(order.items) { foodInCard ->
                        AsyncImage(
                            model = foodInCard.foodWithRestaurant.food.imagePath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            error = painterResource(id = R.drawable.error)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonCustom(
                        text = R.string.confirm,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(max = 80.dp),
                        onClick = {
                            onClickConfirm(order)
                        }
                    )
                    OutlinedButton(
                        onClick = {
                            onClickCancel(order)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(max = 80.dp)
                            .padding(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                )
            }
        }
    }
}

@Composable
fun HistoryOrder(
    modifier: Modifier = Modifier,
    orders: List<OrderWithFirebase>
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        items(orders) { order ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextCustomInputText(
                        text = "$${order.totalPrice}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = MaterialTheme.colorScheme.background
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(2.dp)
                            .height(18.dp)
                            .background(color = Color.LightGray)
                    )
                    TextCustomInputText(
                        text = order.items.size.toString() + " item - " + formatDate(order.timestamp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    TextCustomInputText(
                        text = order.status,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = if (order.status == "Completed") Color.Green else Color.Red
                    )
                }
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(order.items) { foodInCard ->
                        AsyncImage(
                            model = foodInCard.foodWithRestaurant.food.imagePath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp),
                            error = painterResource(id = R.drawable.error)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(max = 80.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            stringResource(R.string.rate),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    ButtonCustom(
                        text = R.string.re_order,
                        modifier = Modifier
                            .weight(1.2f)
                            .heightIn(max = 80.dp),
                        onClick = {}
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                )
            }
        }
    }
}
