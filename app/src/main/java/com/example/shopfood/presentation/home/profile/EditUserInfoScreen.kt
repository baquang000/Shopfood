package com.example.shopfood.presentation.home.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.viewmodel.home.UserViewModel
import com.example.shopfood.ui.theme.backgroundTextField

@Composable
fun EditUserInfoScreen(
    userViewModel: UserViewModel,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userViewModel.loadUser()
    }
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            SimpleTopBarWithBackIcon(
                modifier = Modifier.fillMaxWidth(),
                onBackClick = onBackClick,
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.background
                ),
                iconColor = MaterialTheme.colorScheme.background,
                backgroundColor = MaterialTheme.colorScheme.surface,
                backgroundIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                title = stringResource(R.string.edit_profile),
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                ButtonCustom(
                    text = R.string.save,
                    onClick = {
                        userViewModel.saveUser { success, error ->
                            if (success) {
                                Toast.makeText(context, "Đã lưu", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Lỗi: $error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TextCustom(
                text = R.string.full_name
            )
            OutlinedTextField(
                value = userViewModel.fullName,
                onValueChange = {
                    userViewModel.fullName = it
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = backgroundTextField,
                    focusedContainerColor = backgroundTextField,
                    unfocusedContainerColor = backgroundTextField,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextCustom(
                text = R.string.email
            )
            OutlinedTextField(
                value = userViewModel.email,
                onValueChange = {
                    userViewModel.email = it
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = backgroundTextField,
                    focusedContainerColor = backgroundTextField,
                    unfocusedContainerColor = backgroundTextField,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextCustom(
                text = R.string.phone_number
            )
            OutlinedTextField(
                value = userViewModel.phoneNumber,
                onValueChange = {
                    userViewModel.phoneNumber = it
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = backgroundTextField,
                    focusedContainerColor = backgroundTextField,
                    unfocusedContainerColor = backgroundTextField,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextCustom(
                text = R.string.bio
            )
            OutlinedTextField(
                value = userViewModel.bio,
                onValueChange = {
                    userViewModel.bio = it
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = backgroundTextField,
                    focusedContainerColor = backgroundTextField,
                    unfocusedContainerColor = backgroundTextField,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}