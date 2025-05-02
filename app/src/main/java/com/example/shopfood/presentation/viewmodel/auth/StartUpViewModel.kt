package com.example.shopfood.presentation.viewmodel.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.presentation.navigation.Graph
import com.example.shopfood.until.RememberMePreference
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartUpViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _startDestination: MutableState<String?> = mutableStateOf(null)
    val startDestination: State<String?> = _startDestination

    init {
        viewModelScope.launch {
            val isRemembered = RememberMePreference.getRememberMe(app)
            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

            _startDestination.value = if (isRemembered && isLoggedIn) {
                Graph.MAIN
            } else {
                Graph.AUTH
            }
        }
    }
}

