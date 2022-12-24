package com.example.carapi.repository.calculator

import com.example.carapi.models.Measurement
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    CalculatorRepository {


    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun addMeasurement(measurement: Measurement) {
        db.collection("measurement").add(measurement)
    }
}