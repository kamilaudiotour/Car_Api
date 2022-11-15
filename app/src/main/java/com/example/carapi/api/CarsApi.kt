package com.example.carapi.api

import com.example.carapi.models.Car
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsApi {

    @GET("cars/makes")
    suspend fun getCarsMakes(
    ): Response<MutableList<String>>

    @GET("cars")
    suspend fun getCarsModels(
        @Query("limit")
        limit: String,
        @Query("page")
        page: String,
        @Query("make")
        make: String,
        @Query("model")
        model: String
    ): Response<MutableList<Car>>

}