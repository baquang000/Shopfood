package com.example.shopfood.data.remote.firebase.repository

import android.util.Log
import com.example.shopfood.domain.model.Restaurant
import com.example.shopfood.domain.model.RestaurantState
import com.example.shopfood.domain.repository.firebase.RestaurantRepository
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

class RestaurantRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : RestaurantRepository {

    override fun getAllRestaurant(): Flow<RestaurantState> = callbackFlow {
        val query = firebaseDatabase.getReference("Restaurant")
        trySend(RestaurantState.Loading).isSuccess
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tempList = snapshot.children.mapNotNull { dataSnap ->
                        dataSnap.getValue(Restaurant::class.java)
                    }

                    trySend(RestaurantState.Success(tempList)).isSuccess
                } else {
                    trySend(RestaurantState.Failure("No data found")).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(RestaurantState.Failure(error.message)).isSuccess
            }
        }

        query.addListenerForSingleValueEvent(listener)

        awaitClose { query.removeEventListener(listener) }
        print("end")
    }.flowOn(Dispatchers.IO)

}