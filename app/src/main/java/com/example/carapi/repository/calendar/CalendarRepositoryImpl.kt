package com.example.carapi.repository.calendar

import com.example.carapi.util.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.time.LocalDate
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    CalendarRepository {

    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    override fun addDates(date: LocalDate) {
        val dbDate = hashMapOf<String, Any>()
        dbDate["date"] = date.toString()
        db.collection("busy days").document().set(dbDate)
    }

    override suspend fun getDates(): List<String> {
        val dateList = mutableListOf<String>()
        val busyDaysRef = db.collection("busy days")
        val snapshot = busyDaysRef.get().await()
        val docs = snapshot.documents
        docs.forEach {
            val date = it.getString("date")
            if (date != null) {
                dateList.add(date)
            }

        }
        return dateList
    }
}