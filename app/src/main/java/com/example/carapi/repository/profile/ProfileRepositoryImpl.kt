package com.example.carapi.repository.profile

import android.util.Log
import com.example.carapi.models.Car
import com.example.carapi.util.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    ProfileRepository {



    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun addCar(
        userId: String,
        car: Car
    ) {


        db.collection("users data").document(userId).collection("cars").document().set(car)
            .addOnSuccessListener { Log.d("firebase save", "DocumentSnapshot succesfully written") }
            .addOnFailureListener { e -> Log.w("firebase save", "Error writing document", e) }

    }

    override suspend fun readCarsData(userId: String): List<Car> {
        val carList = mutableListOf<Car>()
        val carsRef = db.collection("users data").document(userId).collection("cars")
        val snapshot = carsRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val car = it.toObject(Car::class.java)
            if (car != null) {
                carList.add(car)
                Log.d("firebase listen", carList.toString())
            }
        }

        return carList
    }
}