package com.example.carapi.repository.profile

import com.example.carapi.models.Car

interface ProfileRepository {



    suspend fun readCarsData(): List<Car>

    fun deleteCar(car: Car)


}