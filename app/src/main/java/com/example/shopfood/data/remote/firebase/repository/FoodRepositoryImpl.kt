package com.example.shopfood.data.remote.firebase.repository

import com.example.shopfood.domain.model.Food
import com.example.shopfood.domain.model.FoodState
import com.example.shopfood.domain.model.FoodWithRestaurant
import com.example.shopfood.domain.repository.firebase.FoodRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : FoodRepository {

    override fun getAllFood(): Flow<FoodState> = callbackFlow {
        val query = firebaseDatabase.getReference("Foods")
        trySend(FoodState.Loading).isSuccess

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val result = mutableListOf<FoodWithRestaurant>()

                    for (restaurantSnap in snapshot.children) {
                        val restaurantId = restaurantSnap.key ?: continue

                        for (foodSnap in restaurantSnap.children) {
                            val food = foodSnap.getValue(Food::class.java)
                            if (food != null) {
                                result.add(FoodWithRestaurant(food, restaurantId))
                            }
                        }
                    }

                    if (result.isNotEmpty()) {
                        trySend(FoodState.Success(result)).isSuccess
                    } else {
                        trySend(FoodState.Empty).isSuccess
                    }
                } else {
                    trySend(FoodState.Empty).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(FoodState.Failure(error.message)).isSuccess
            }
        }

        query.addListenerForSingleValueEvent(listener)

        awaitClose { query.removeEventListener(listener) }
    }.flowOn(Dispatchers.IO)


}