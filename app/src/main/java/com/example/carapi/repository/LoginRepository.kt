package com.example.carapi.repository

import com.example.carapi.util.Resource
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String) : Resource<FirebaseUser>
    suspend fun register(email: String, name: String, password: String) : Resource<FirebaseUser>
    fun logout()
}