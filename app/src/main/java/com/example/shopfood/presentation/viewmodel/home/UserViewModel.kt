package com.example.shopfood.presentation.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopfood.domain.model.User
import com.example.shopfood.domain.usecase.firebase.home.user.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userUseCases: UserUseCases
) : ViewModel() {

    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var bio by mutableStateOf("")

    var isSaving by mutableStateOf(false)
        private set

    fun loadUser() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            userUseCases.getUser(userId)?.let {
                fullName = it.fullName
                email = it.email
                phoneNumber = it.phoneNumber
                bio = it.bio
            }
        }
    }


    fun saveUser(onDone: (Boolean, String?) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onDone(false, "Not logged in")
        val user = User(
            id = userId,
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber,
            bio = bio
        )
        viewModelScope.launch {
            isSaving = true
            val result = userUseCases.updateUser(user)
            isSaving = false
            if (result.isSuccess) {
                onDone(true, null)
            } else {
                onDone(false, result.exceptionOrNull()?.message)
            }
        }
    }
}
