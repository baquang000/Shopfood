package com.example.shopfood.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextFieldCustom
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun SignInScreen() {
    ScaffoldWithIconInTopBar(
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .background(color = MaterialTheme.colorScheme.onBackground)
            ) {
                TextCustom(
                    text = R.string.sign_up,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                )
                TextCustom(
                    text = R.string.sign_up_description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 48.dp)
                )
                SessionSignUpTwo()
            }
        }
    )
}

@Composable
fun SessionSignUpTwo(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var isShow by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(
                color = MaterialTheme.colorScheme.onSecondary
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            TextCustom(
                text = R.string.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                textAlign = TextAlign.Start
            )
            TextFieldCustom(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier,
                placeholder = "John doe",
            )

            TextCustom(
                text = R.string.login_email,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                textAlign = TextAlign.Start
            )
            TextFieldCustom(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier,
                placeholder = "example@gmail.com",
            )
            TextCustom(
                text = R.string.login_password,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                textAlign = TextAlign.Start
            )
            TextFieldCustom(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier,
                placeholder = " * * * * * * *",
                trailingIcon = {
                    IconButton(onClick = { isShow = !isShow }) {
                        Icon(
                            if (isShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            "Visibility",
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    }
                },
                visualTransformation = if (isShow) VisualTransformation.None else PasswordVisualTransformation()
            )
            TextCustom(
                text = R.string.re_type_password,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                textAlign = TextAlign.Start
            )
            TextFieldCustom(
                value = rePassword,
                onValueChange = { rePassword = it },
                modifier = Modifier,
                placeholder = " * * * * * * *",
                trailingIcon = {
                    IconButton(onClick = { isShow = !isShow }) {
                        Icon(
                            if (isShow) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            "Visibility",
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    }
                },
                visualTransformation = if (isShow) VisualTransformation.None else PasswordVisualTransformation()
            )
            ButtonCustom(
                text = R.string.sign_up,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    ShopfoodTheme {
        SignInScreen()
    }
}