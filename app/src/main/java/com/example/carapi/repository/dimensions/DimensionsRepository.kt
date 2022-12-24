package com.example.carapi.repository.dimensions

import com.example.carapi.models.Measurement

interface DimensionsRepository {

    suspend fun getMeasurement(): List<Measurement>
}