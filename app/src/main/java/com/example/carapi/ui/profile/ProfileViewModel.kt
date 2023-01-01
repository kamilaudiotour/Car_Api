package com.example.carapi.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Car
import com.example.carapi.models.Order
import com.example.carapi.repository.order.CreateOrderRepository
import com.example.carapi.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository, private val createOrderRepository: CreateOrderRepository) :
    ViewModel() {

    private val _profileCars = MutableLiveData<List<Car>>()
    val profileCars: LiveData<List<Car>>
        get() = _profileCars

    private val _ordersByUser = MutableLiveData<List<Order>>()
    val ordersByUser: LiveData<List<Order>>
        get() = _ordersByUser

    fun getOrdersByUser() =
        viewModelScope.launch {
            _ordersByUser.value = createOrderRepository.getOrdersByUser()
        }


    fun readCarsData() {
        viewModelScope.launch {
            _profileCars.value = profileRepository.readCarsData()
        }
    }

    fun deleteCar(car: Car) {
        profileRepository.deleteCar(car)
    }


}