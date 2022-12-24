package com.example.carapi.repository.dimensions

import com.example.carapi.models.Measurement
import com.example.carapi.util.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import javax.inject.Inject

class DimensionsRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    DimensionsRepository {

    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override suspend fun getMeasurement(): List<Measurement> {
        val measurementList = mutableListOf<Measurement>()
        val measurementRef = db.collection("measurement")
        val snapshot = measurementRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val measurement = it.toObject(Measurement::class.java)
            if (measurement != null) {
                measurementList.add(measurement)
            }
        }
        return measurementList
    }
}