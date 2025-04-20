package com.example.shopfood.data.remote.firebase.repository

import com.example.shopfood.domain.model.AuthResult
import com.example.shopfood.domain.repository.firebase.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun login(email: String, password: String): Flow<AuthResult> = flow {
        try {
            emit(AuthResult.Loading)
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                // Save user session if needed
                emit(AuthResult.Success(user))
            } ?: emit(AuthResult.Failure("Authentication failed"))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(AuthResult.Failure("User not found"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(AuthResult.Failure("Invalid email or password"))
        } catch (e: Exception) {
            emit(AuthResult.Failure(e.localizedMessage ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun signup(
        name: String,
        email: String,
        password: String
    ): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                user.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                ).await()
                emit(AuthResult.Success(user))
            } else {
                emit(AuthResult.Failure("User is null"))
            }
        } catch (e: Exception) {
            emit(AuthResult.Failure(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}