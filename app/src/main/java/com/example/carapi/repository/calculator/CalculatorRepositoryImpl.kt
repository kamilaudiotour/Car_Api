package com.example.carapi.repository.calculator

import com.example.carapi.models.Measurement
import com.example.carapi.util.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class CalculatorRepositoryImpl @Inject constructor(private val db: FirebaseFirestore, private val auth: FirebaseAuth) :
    CalculatorRepository {


    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun addMeasurement(measurement: Measurement) {
        measurement.by = auth.currentUser?.displayName.toString()
        db.collection("measurement").add(measurement)
    }




}