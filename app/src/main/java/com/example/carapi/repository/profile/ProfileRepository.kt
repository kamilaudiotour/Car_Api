package com.example.carapi.repository.profile

import com.example.carapi.models.Car

interface ProfileRepository {

    fun addCar(
        userId: String,
        car: Car
    )

    fun readCarsData(userId: String) : List<Car>
}