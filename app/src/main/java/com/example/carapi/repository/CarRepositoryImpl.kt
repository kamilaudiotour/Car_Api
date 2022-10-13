package com.example.carapi.repository

import com.example.carapi.api.CarsApi
import com.example.carapi.models.Car
import retrofit2.Response
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val api: CarsApi) : CarRepository {

    override suspend fun getCars(
        limit: String,
        page: String
    ): Response<MutableList<Car>> {
        return api.getCars(limit, page)
    }

    override suspend fun getCarsByMake(
        limit: String,
        page: String,
        make: String,
        model: String,
        type: String
    ): Response<MutableList<Car>> {
        return api.getCarByMake(limit, page, make, model, type)
    }

}