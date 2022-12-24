package com.example.carapi.repository.calculator

import com.example.carapi.models.Measurement

interface CalculatorRepository {



    fun addMeasurement(measurement: Measurement)
}