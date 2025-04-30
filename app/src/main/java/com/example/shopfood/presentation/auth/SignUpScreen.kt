package com.example.shopfood.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shopfood.R
import com.example.shopfood.data.remote.firebase.repository.AuthRepositoryImpl
import com.example.shopfood.domain.model.AuthResult
import com.example.shopfood.domain.usecase.firebase.auth.AuthUseCases
import com.example.shopfood.domain.usecase.firebase.auth.LoginUseCase
import com.example.shopfood.domain.usecase.firebase.auth.SignupUseCase
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.ScaffoldWithIconInTopBar
import com.example.shopfood.presentation.component.SimpleTopBarWithBackIcon
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.presentation.component.TextFieldCustom
import com.example.shopfood.presentation.viewmodel.auth.SignUpViewModel
import com.example.shopfood.ui.theme.ShopfoodTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onLogin: () -> Unit = {}
) {
    ScaffoldWithIconInTopBar(
        topBar = {
            SimpleTopBarWithBackIcon(
                backgroundColor = MaterialTheme.colorScheme.background,
                backgroundIconColor = MaterialTheme.colorScheme.surface,
                onBackClick = onLogin
            )
        },
        content = { paddingValue ->
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    TextCustom(
                        text = R.string.sign_up,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                    TextCustom(
                        text = R.string.sign_up_description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 24.dp)
                    )
                    SessionSignUpTwo(
                        viewModel = signUpViewModel,
                        onLogin = onLogin
                    )
                }

            }
        }
    )
}

@Composable
fun SessionSignUpTwo(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    onLogin: () -> Unit
) {
    val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val rePassword = viewModel.rePassword
    val error = viewModel.error
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRePasswordVisible by remember { mutableStateOf(false) }

    val signInState by viewModel.signInState.collectAsStateWithLifecycle()
    LaunchedEffect(signInState) {
        if (signInState is AuthResult.Success) {
            onLogin()
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
                text = R.string.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                textAlign = TextAlign.Start
            )
            TextFieldCustom(
                value = name,
                onValueChange = viewModel::onNameChange,
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
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            "Visibility",
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
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
                onValueChange = viewModel::onRePasswordChange,
                modifier = Modifier,
                placeholder = " * * * * * * *",
                trailingIcon = {
                    IconButton(onClick = { isRePasswordVisible = !isRePasswordVisible }) {
                        Icon(
                            if (isRePasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            "Visibility",
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    }
                },
                visualTransformation = if (isRePasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            when {
                error -> {
                    TextCustom(
                        text = R.string.Password_not_match,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                signInState is AuthResult.Failure -> {
                    Text(
                        text = (signInState as AuthResult.Failure).error,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                else -> Spacer(modifier = Modifier.height(24.dp))
            }
            ButtonCustom(
                text = R.string.sign_up,
                onClick = viewModel::signUp,
                modifier = Modifier.padding(bottom = 24.dp),
                enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank()
            )
        }
        if (signInState is AuthResult.Loading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    ShopfoodTheme {
        SignUpScreen(
            signUpViewModel = SignUpViewModel(
                AuthUseCases(
                    LoginUseCase(
                        AuthRepositoryImpl(
                            FirebaseAuth.getInstance()
                        )
                    ), SignupUseCase(AuthRepositoryImpl(FirebaseAuth.getInstance()))
                )
            )
        )
    }
}