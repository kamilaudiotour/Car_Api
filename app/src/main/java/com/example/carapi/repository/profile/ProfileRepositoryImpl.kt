package com.example.carapi.repository.profile

import android.util.Log
import com.example.carapi.models.Car
import com.example.carapi.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    ProfileRepository {

    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }



    override suspend fun readCarsData(): List<Car> {
        val carList = mutableListOf<Car>()
        val carsRef = db.collection("users data").document(auth.currentUser?.uid.toString())
            .collection("cars")
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

    override fun deleteCar(car: Car) {
        db.collection("users data").document(auth.currentUser?.uid.toString()).collection("cars")
            .document(car.id.toString())
            .delete()
    }

}