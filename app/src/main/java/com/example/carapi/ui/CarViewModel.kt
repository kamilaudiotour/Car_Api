package com.example.carapi.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Car
import com.example.carapi.repository.CarRepository
import com.example.carapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CarViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    val cars: MutableLiveData<Resource<List<Car>>> = MutableLiveData()
    var page = 1

    init {
        getCars("50")
    }

    private fun getCars(limit: String) = viewModelScope.launch {
        cars.postValue(Resource.Loading())
        val response = carRepository.getCars(limit, page.toString())
        cars.postValue(handleCarResponse(response))

    }

    private fun handleCarResponse(response: Response<List<Car>>): Resource<List<Car>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Log.d("response", response.body().toString())
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}