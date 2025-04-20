package com.example.shopfood.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.domain.model.AuthResult
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.ScaffoldWithNoSafeArea
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextFieldCustom
import com.example.shopfood.presentation.viewmodel.auth.LoginViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onSignUp: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    ScaffoldWithNoSafeArea(
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                TextCustom(
                    text = R.string.login_title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = screenHeight * 0.1f)
                )
                TextCustom(
                    text = R.string.login_description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 48.dp)
                )
                SessionLoginTwo(
                    modifier = Modifier.fillMaxSize(),
                    onSignUp = onSignUp,
                    onLoginSuccess = onLoginSuccess,
                    viewModel = loginViewModel
                )
            }
        }
    )
}

@Composable
fun SessionLoginTwo(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onSignUp: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val email = viewModel.email
    val password = viewModel.password
    var checked by remember { mutableStateOf(false) }
    var isShow by remember { mutableStateOf(false) }
    LaunchedEffect(loginState) {
        if (loginState is AuthResult.Success) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
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
                onValueChange = viewModel::onEmailChange,
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
                onValueChange = viewModel::onPasswordChange,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            checked = !checked
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            checkmarkColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    TextCustom(
                        text = R.string.Remember_me,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier,
                        textAlign = TextAlign.Start
                    )
                }
                TextCustom(
                    text = R.string.Forgot_password,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier,
                    textAlign = TextAlign.Start
                )
            }
            if (loginState is AuthResult.Failure) {
                Text(
                    text = (loginState as AuthResult.Failure).error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            } else Spacer(modifier = Modifier.height(12.dp))

            ButtonCustom(
                text = R.string.login_title,
                onClick = viewModel::login,
                enabled = email.isNotBlank() && password.isNotBlank()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextCustom(
                    text = R.string.Login_have_question,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(Modifier.width(8.dp))
                TextCustom(
                    text = R.string.sign_up,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onSignUp() }
                )
            }
            TextCustom(
                text = R.string.or,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 24.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Google",
                    modifier = Modifier.size(56.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier.size(56.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.phone_call),
                    contentDescription = "Google",
                    modifier = Modifier.size(56.dp)
                )
            }
        }
        if (loginState is AuthResult.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ShopfoodTheme {
//        LoginScreen(onLoginSuccess = {}, onSignUp = {})
    }
}