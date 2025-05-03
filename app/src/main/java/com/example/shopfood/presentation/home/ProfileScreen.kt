package com.example.shopfood.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.component.TextCustom

@Composable
fun ProfileScreen(
    onClickUserInfo: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onClickAddress : () -> Unit = {},
    onClickOrder : () -> Unit = {}
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    ScaffoldWithIconInTopBar(
        topBar = {
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.profile),
                onBackClick = onBackClick,
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.background
                ),
                iconColor = MaterialTheme.colorScheme.background,
                backgroundColor = MaterialTheme.colorScheme.surface,
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f)
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .heightIn(min = 60.dp)
                                .clickable { onClickUserInfo() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                            Text(
                                text = stringResource(R.string.private_info_user),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .heightIn(min = 60.dp)
                                .clickable {
                                    onClickAddress()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddHome,
                                    contentDescription = null,
                                    tint = Color.Blue
                                )
                            }
                            Text(
                                text = stringResource(R.string.address),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f)
                    )
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .heightIn(min = 60.dp)
                                .clickable {
                                    onClickOrder()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCartCheckout,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                            Text(
                                text = stringResource(R.string.order),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .heightIn(min = 60.dp)
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.Blue
                                )
                            }
                            Text(
                                text = stringResource(R.string.favourite),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .heightIn(min = 60.dp)
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PunchClock,
                                    contentDescription = null,
                                    tint = Color.Blue
                                )
                            }
                            Text(
                                text = stringResource(R.string.change_pass_user),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.background
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 50.dp)
                        .clickable {
                            openDialog = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Output,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    Text(
                        text = stringResource(R.string.logout),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    )

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                TextCustom(
                    text = R.string.confirm,
                )
            },
            text = {
                Text(text = "Bạn có muốn đăng xuất không?")
            },
            confirmButton = {
                Button(onClick = {
                    openDialog = false
                }) {
                    TextCustom(
                        text = R.string.yes,
                    )
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog = false
                }) {
                    TextCustom(
                        text = R.string.cancel,
                    )
                }
            })
    }
}