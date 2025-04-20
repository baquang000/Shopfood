package com.example.shopfood.di

import com.example.shopfood.data.remote.firebase.repository.AuthRepositoryImpl
import com.example.shopfood.domain.repository.firebase.AuthRepository
import com.example.shopfood.domain.usecase.firebase.auth.AuthUseCases
import com.example.shopfood.domain.usecase.firebase.auth.LoginUseCase
import com.example.shopfood.domain.usecase.firebase.auth.SignupUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            login = LoginUseCase(authRepository),
            signup = SignupUseCase(authRepository),
        )
    }
}