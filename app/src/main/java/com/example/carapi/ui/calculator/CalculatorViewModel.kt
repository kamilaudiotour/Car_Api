package com.example.carapi.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Car
import com.example.carapi.models.Measurement
import com.example.carapi.repository.calculator.CalculatorRepository
import com.example.carapi.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val calculatorRepository: CalculatorRepository
) : ViewModel() {


    val sideLengthData = MutableLiveData(0.0)
    val backLengthData = MutableLiveData(0.0)
    val bumperLengthData = MutableLiveData(0.0)
    val bonnetWidthLengthData = MutableLiveData(0.0)
    val bonnetHeightLengthData = MutableLiveData(0.0)
    val roofWidthLengthData = MutableLiveData(0.0)
    val roofHeightLengthData = MutableLiveData(0.0)

    val result = MutableLiveData(0.0)

    val selectedCar = MutableLiveData<Car?>(null)


    private val _profileCars = MutableLiveData<List<Car>>()
    val profileCars: LiveData<List<Car>>
        get() = _profileCars


    fun getProfileCars() {
        viewModelScope.launch {
            _profileCars.value = profileRepository.readCarsData()
        }

    }

    fun readValidatedForm(
        sideLength: String,
        backLength: String,
        bumperLength: String,
        bonnetWidthLength: String,
        bonnetHeightLength: String,
        roofWidthLength: String,
        roofHeightLength: String
    ) {
        sideLengthData.value = sideLength.toDouble()
        backLengthData.value = backLength.toDouble()
        bumperLengthData.value = bumperLength.toDouble()
        bonnetWidthLengthData.value = bonnetWidthLength.toDouble()
        bonnetHeightLengthData.value = bonnetHeightLength.toDouble()
        roofWidthLengthData.value = roofWidthLength.toDouble()
        roofHeightLengthData.value = roofHeightLength.toDouble()
    }


    fun calculateResult() {

        result.value =
            (2 * (sideLengthData.value!!)).plus(bumperLengthData.value!!)
                .plus(bonnetHeightLengthData.value!!).plus(roofHeightLengthData.value!!).plus(100.0)
                .div(100.0)

    }

    fun onCarClicked(car: Car) {
        selectedCar.value = car
    }

    fun addMeasurement() {
        val measurement = Measurement(selectedCar.value!!, result.value.toString())
        calculatorRepository.addMeasurement(measurement)
    }

    fun resetData() {
        selectedCar.value = null
        result.value = null
    }

}