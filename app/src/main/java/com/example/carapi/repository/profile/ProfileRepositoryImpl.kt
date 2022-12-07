package com.example.carapi.repository.profile

import android.util.Log
import com.example.carapi.models.Car
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
            .addOnFailureListener { e -> Log.w("firebase save","Error writing document", e) }

    }

    override fun readCarsData(userId: String): List<Car> {
        var list = mutableListOf<Car>()
        db.collection("users data").document(userId).collection("cars").get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("firebase save", "${document.id} => ${document.data}")
            }
        }
            .addOnFailureListener { exception ->
                Log.w("firebase save", "Error getting documents: ", exception)
            }
        return list
    }
}