package com.example.carapi.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Car
import com.example.carapi.models.Order
import com.example.carapi.repository.order.CreateOrderRepository
import com.example.carapi.repository.profile.ProfileRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val createOrderRepository: CreateOrderRepository
) : ViewModel() {

    val currentUser: FirebaseUser?
        get() = createOrderRepository.currentUser

    val selectedCar = MutableLiveData<Car?>(null)


    private val _profileCars = MutableLiveData<List<Car>>()
    val profileCars: LiveData<List<Car>>
        get() = _profileCars

    private val _allOrders = MutableLiveData<List<Order>>()
    val allOrders: LiveData<List<Order>>
        get() = _allOrders

    private val _isUserAdmin = MutableLiveData<Boolean>()
    val isUserAdmin: LiveData<Boolean>
        get() = _isUserAdmin

    fun getAllOrders() {
        viewModelScope.launch {
            _allOrders.value = createOrderRepository.getAllOrders()
        }
    }

    fun getProfileCars() {
        viewModelScope.launch {
            _profileCars.value = profileRepository.readCarsData()
        }
    }

    fun onCarClicked(car: Car) {
        selectedCar.value = car
    }

    fun addOrder(name: String, phoneNumber: String, remarks: String, foilVariant: String) {
        val order = Order(
            car = selectedCar.value!!,
            name = name,
            phone = phoneNumber,
            foilVariant = foilVariant,
            remarks = remarks,
            email = currentUser?.email!!
        )
        createOrderRepository.addOrder(order)
    }

    fun isUserAdmin() {
        viewModelScope.launch {
            _isUserAdmin.value = createOrderRepository.isUserAdmin()
        }
    }




}