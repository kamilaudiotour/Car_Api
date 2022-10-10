package com.example.carapi.repository

import com.example.carapi.api.CarsApi
import com.example.carapi.api.RetrofitInstance
import javax.inject.Inject

class CarRepository @Inject constructor(val api: CarsApi) {

    suspend fun getCars(limit: String, page: String) = api.getCars(limit,page)
}