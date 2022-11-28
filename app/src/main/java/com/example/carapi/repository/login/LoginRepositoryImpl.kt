package com.example.carapi.repository.login

import android.widget.Toast
import com.example.carapi.util.Resource
import com.example.carapi.util.await
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    LoginRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun register(
        email: String,
        name: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun resetPassword(email: String) : Resource<String>? {
        var result : Resource<String>? = null
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            result = if (task.isSuccessful) {
                Resource.Success("Email wyslany pomyslnie")
            } else {
                Resource.Error(task.exception?.message.toString())
            }

        }
        return result
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}