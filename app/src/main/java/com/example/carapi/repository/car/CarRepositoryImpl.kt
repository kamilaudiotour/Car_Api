package com.example.carapi.repository.car

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.carapi.api.CarsApi
import com.example.carapi.models.Car
import com.example.carapi.paging.CarPagingSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import retrofit2.Response
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val api: CarsApi, private val db: FirebaseFirestore, private val auth: FirebaseAuth) : CarRepository {

    override suspend fun getCarsMakes(
    ): Response<MutableList<String>> {
        return api.getCarsMakes()
    }

    init {
        db.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun addCar(
        car: Car
    ) {

        db.collection("users data").document(auth.currentUser?.uid.toString()).collection("cars")
            .document(car.id.toString())
            .set(car)
            .addOnSuccessListener { Log.d("firebase save", "DocumentSnapshot succesfully written") }
            .addOnFailureListener { e -> Log.w("firebase save", "Error writing document", e) }

    }

    override suspend fun getCarsModels(
        limit: String,
        page: String,
        make: String,
        model: String
    ): Response<MutableList<Car>> {
        return api.getCarsModels(limit, page, make, model)
    }


    override fun carPagingSource(make: String, model: String) = Pager(config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false,
    ),
        pagingSourceFactory = { CarPagingSource(api, make, model) }
    ).liveData

    companion object {
        val NETWORK_PAGE_SIZE = 10
    }
}