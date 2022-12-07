package com.example.carapi.ui.profile

import androidx.lifecycle.ViewModel
import com.example.carapi.models.Car
import com.example.carapi.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) : ViewModel() {

    fun saveCar(userId: String, car: Car){
        profileRepository.addCar(userId, car)
    }

    fun readCarsData(userId: String){
        profileRepository.readCarsData(userId)
    }

}