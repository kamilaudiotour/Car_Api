package com.example.carapi

import android.app.Application
import com.example.carapi.repository.CarRepository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CarApplication : Application() {
    //val repository: CarRepository by lazy {CarRepository()}
}