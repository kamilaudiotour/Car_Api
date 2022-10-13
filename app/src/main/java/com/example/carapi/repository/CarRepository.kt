package com.example.carapi.repository

import com.example.carapi.models.Car
import retrofit2.Response

interface CarRepository {

    suspend fun getCars(
        limit: String,
        page: String
    ): Response<MutableList<Car>>

    suspend fun getCarsByMake(
        limit: String,
        page: String,
        make: String,
        model: String,
        type: String
    ): Response<MutableList<Car>>

}