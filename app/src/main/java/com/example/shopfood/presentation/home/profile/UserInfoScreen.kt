package com.example.shopfood.presentation.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextCustomInputText
import com.example.shopfood.presentation.viewmodel.home.UserViewModel

@Composable
fun UserInfoScreen(
    userViewModel: UserViewModel,
    onBackClick: () -> Unit = {},
    onClickEdit: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        userViewModel.loadUser()
    }

    Scaffold(
        topBar = {
            TopBarUserInfo(onBackClick = onBackClick, onClickEdit = onClickEdit)
        }
    ) { paddingValue ->
        Column(
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f)
                )
            ) {
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
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        TextCustom(
                            text = R.string.full_name
                        )
                        TextCustomInputText(
                            text = userViewModel.fullName
                        )
                    }
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
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = Color.Blue
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        TextCustom(
                            text = R.string.email
                        )
                        TextCustomInputText(
                            text = userViewModel.email
                        )
                    }
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
                            imageVector = Icons.Filled.Phone,
                            contentDescription = null,
                            tint = Color.Blue
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        TextCustom(
                            text = R.string.phone_number
                        )
                        TextCustomInputText(
                            text = userViewModel.phoneNumber
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUserInfo(
    onBackClick: () -> Unit,
    onClickEdit: () -> Unit
) {
    TopAppBar(
        title = {
            TextCustom(
                text = R.string.person_info
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