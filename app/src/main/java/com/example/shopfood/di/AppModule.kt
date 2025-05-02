package com.example.shopfood.di

import com.example.shopfood.data.remote.firebase.repository.AuthRepositoryImpl
import com.example.shopfood.data.remote.firebase.repository.FoodRepositoryImpl
import com.example.shopfood.data.remote.firebase.repository.OrderRepositoryImpl
import com.example.shopfood.data.remote.firebase.repository.RestaurantRepositoryImpl
import com.example.shopfood.data.remote.firebase.repository.UserRepositoryImpl
import com.example.shopfood.domain.repository.firebase.AuthRepository
import com.example.shopfood.domain.repository.firebase.FoodRepository
import com.example.shopfood.domain.repository.firebase.OrderRepository
import com.example.shopfood.domain.repository.firebase.RestaurantRepository
import com.example.shopfood.domain.repository.firebase.UserRepository
import com.example.shopfood.domain.usecase.firebase.auth.AuthUseCases
import com.example.shopfood.domain.usecase.firebase.auth.LoginUseCase
import com.example.shopfood.domain.usecase.firebase.auth.SignupUseCase
import com.example.shopfood.domain.usecase.firebase.home.food.FoodUseCases
import com.example.shopfood.domain.usecase.firebase.home.food.GetAllFoodUseCase
import com.example.shopfood.domain.usecase.firebase.home.order.OrderUseCases
import com.example.shopfood.domain.usecase.firebase.home.order.SaveOrderUseCase
import com.example.shopfood.domain.usecase.firebase.home.restaurant.GetAllRestaurantUseCase
import com.example.shopfood.domain.usecase.firebase.home.restaurant.RestaurantUseCases
import com.example.shopfood.domain.usecase.firebase.home.user.GetUserUseCase
import com.example.shopfood.domain.usecase.firebase.home.user.UpdateUserUseCase
import com.example.shopfood.domain.usecase.firebase.home.user.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun provideFirebaseDatabase(): FirebaseDatabase =
        FirebaseDatabase.getInstance("https://shopfood-46c3a-default-rtdb.asia-southeast1.firebasedatabase.app")

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideRestaurantRepository(firebaseDatabase: FirebaseDatabase): RestaurantRepository {
        return RestaurantRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(firebaseDatabase: FirebaseDatabase): FoodRepository {
        return FoodRepositoryImpl(firebaseDatabase)
    }

    @Provides
    fun provideOrderRepository(firebaseDatabase: FirebaseDatabase): OrderRepository {
        return OrderRepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: FirebaseDatabase): UserRepository =
        UserRepositoryImpl(database)

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            login = LoginUseCase(authRepository),
            signup = SignupUseCase(authRepository),
        )
    }

    @Provides
    @Singleton
    fun provideRestaurantUseCases(restaurantRepository: RestaurantRepository): RestaurantUseCases {
        return RestaurantUseCases(
            getAllRestaurant = GetAllRestaurantUseCase(restaurantRepository)
        )
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(foodRepository: FoodRepository): FoodUseCases {
        return FoodUseCases(
            getAllFood = GetAllFoodUseCase(foodRepository)
        )
    }

    @Provides
    @Singleton
    fun provideOrderUseCases(orderRepository: OrderRepository): OrderUseCases {
        return OrderUseCases(
            saveOrder = SaveOrderUseCase(orderRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(userRepository: UserRepository): UserUseCases =
        UserUseCases(
            getUser = GetUserUseCase(userRepository),
            updateUser = UpdateUserUseCase(userRepository)
        )
}