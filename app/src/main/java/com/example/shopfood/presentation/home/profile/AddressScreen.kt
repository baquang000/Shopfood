package com.example.shopfood.presentation.home.profile

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.domain.model.Address
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.viewmodel.home.UserViewModel
import com.example.shopfood.ui.theme.backgroundTextField

@Composable
fun AddressScreen(
    userViewModel: UserViewModel,
    onBackClick: () -> Unit = {},
    onClickEdit: () -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        userViewModel.getAddresses()
    }
    Scaffold(
        topBar = {
            TopBarAddress(
                onBackClick = onBackClick,
                onClickEdit = {
                    userViewModel.selectAddress(null)
                    onClickEdit()
                }
            )
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
                .padding(
                    vertical = 12.dp, horizontal = 12.dp
                )
        ) {
            items(userViewModel.addressList) { address ->
                ItemAddress(
                    modifier = Modifier.padding(bottom = 12.dp),
                    address = address,
                    onClickEdit = {
                        userViewModel.selectAddress(address)
                        onClickEdit()
                    },
                    onClickDelete = {
                        userViewModel.deleteAddress(address) { success, message ->
                            if (!success) {
                                Toast.makeText(
                                    context,
                                    message ?: "Xoá thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddress(
    onBackClick: () -> Unit,
    onClickEdit: () -> Unit
) {
    TopAppBar(
        title = {
            TextCustom(
                text = R.string.my_address
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, end = 24.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f))
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            Text(
                text = stringResource(R.string.edit),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable {
                        onClickEdit()
                    }
            )
        }
    )
}

@Composable
fun ItemAddress(
    modifier: Modifier = Modifier,
    address: Address,
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundTextField
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp)
                .heightIn(min = 100.dp)
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
                    imageVector = if (address.label == "Home") Icons.Filled.Home else if (address.label == "Work") Icons.Filled.Work else Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextCustomInputText(
                        text = address.label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.W600,
                        ),
                        color = MaterialTheme.colorScheme.background
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = onClickEdit,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EditNote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = onClickDelete,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                TextCustomInputText(
                    text = address.address
                )
            }
        }
    }
}