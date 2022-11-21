package com.example.carapi.repository.car

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.carapi.models.Car
import retrofit2.Response


interface CarRepository {

    suspend fun getCarsMakes(
    ): Response<MutableList<String>>


    suspend fun getCarsModels(
        limit: String,
        page: String,
        make: String,
        model: String
    ): Response<MutableList<Car>>

    fun carPagingSource(make: String, model: String): LiveData<PagingData<Car>>


}