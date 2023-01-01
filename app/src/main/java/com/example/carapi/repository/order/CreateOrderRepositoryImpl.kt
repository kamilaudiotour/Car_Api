package com.example.carapi.repository.order

import com.example.carapi.models.Order
import com.example.carapi.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class CreateOrderRepositoryImpl(private val db: FirebaseFirestore, private val auth: FirebaseAuth) :
    CreateOrderRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser


    override fun addOrder(order: Order) {
        db.collection("orders").document("orders by user").collection("list")
            .document(currentUser?.uid.toString()).collection("user orders").add(order)
        db.collection("orders").document("all orders").collection("list").add(order)
    }


    override suspend fun getOrdersByUser(): List<Order> {
        val ordersList = mutableListOf<Order>()
        val ordersRef = db.collection("orders").document("orders by user").collection("list")
            .document(currentUser?.uid.toString()).collection("user orders")
        val snapshot = ordersRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val order = it.toObject(Order::class.java)
            if (order != null) {
                ordersList.add(order)
            }
        }
        return ordersList
    }

    override suspend fun getAllOrders(): List<Order> {
        val ordersList = mutableListOf<Order>()
        val ordersRef = db.collection("orders").document("all orders").collection("list")
        val snapshot = ordersRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val order = it.toObject(Order::class.java)
            if (order != null) {
                ordersList.add(order)
            }
        }
        return ordersList
    }

    override suspend fun isUserAdmin(): Boolean {
        val userId = auth.currentUser?.uid.toString()
        val userDocRef = db.collection("users data").document(userId)
        val snapshot = userDocRef.get().await()
        val doc = snapshot.data?.get("isAdmin")
        return doc.toString() == "true"
    }

}
