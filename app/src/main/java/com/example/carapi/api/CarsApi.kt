package com.example.carapi.api

import com.example.carapi.models.Car
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsApi {

    @GET("cars")
    suspend fun getCars(
        @Query("limit")
        limit: String = "10",
        @Query("page")
        page: String = "0"
    ): Response<List<Car>>

}