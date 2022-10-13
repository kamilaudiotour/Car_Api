package com.example.carapi.api

import com.example.carapi.models.Car
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsApi {

    @GET("cars")
    suspend fun getCars(
        @Query("limit")
        limit: String ,
        @Query("page")
        page: String
    ): Response<MutableList<Car>>

    @GET("cars")
    suspend fun getCarByMake(
        @Query("limit")
        limit: String,
        @Query("page")
        page: String,
        @Query("make")
        make: String,
        @Query("model")
        model: String,
        @Query("type")
        type: String
    ) : Response<MutableList<Car>>

}