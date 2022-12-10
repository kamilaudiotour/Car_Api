package com.example.carapi.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Car
import com.example.carapi.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private val _profileCars = MutableLiveData<List<Car>>()
    val profileCars: LiveData<List<Car>>
        get() = _profileCars


    fun saveCar(userId: String, car: Car) {
        profileRepository.addCar(userId, car)
    }

    fun readCarsData(userId: String) {
        viewModelScope.launch {
            _profileCars.value = profileRepository.readCarsData(userId)
        }
    }

    fun deleteCar(userId: String, car: Car) {
        profileRepository.deleteCar(userId, car)
    }


}