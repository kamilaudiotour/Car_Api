package com.example.carapi.repository.profile

import com.example.carapi.models.Car

interface ProfileRepository {

    fun addCar(userId: String, car: Car)

    suspend fun readCarsData(userId: String) : List<Car>

    fun deleteCar(userId : String, car: Car)


}