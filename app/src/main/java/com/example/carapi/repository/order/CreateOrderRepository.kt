package com.example.carapi.repository.order

import com.example.carapi.models.Order
import com.google.firebase.auth.FirebaseUser

interface CreateOrderRepository {

    val currentUser: FirebaseUser?

    fun addOrder(order: Order)

    suspend fun getOrdersByUser() : List<Order>

    suspend fun getAllOrders() : List<Order>
    suspend fun isUserAdmin() : Boolean


}