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
    private var page = 0
    private var limit = 10
    var carResponse : MutableList<Car>? = null

    init {
        getCars(limit.toString(), page.toString())
    }

    private fun getCars(limit: String, page: String) = viewModelScope.launch {
        cars.postValue(Resource.Loading())
        val response = carRepository.getCars(limit, page)
        cars.postValue(handleCarResponse(response))

    }

    private fun handleCarResponse(response: Response<MutableList<Car>>): Resource<List<Car>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                page++
                if(carResponse == null){
                    carResponse = resultResponse
                } else {
                    val oldCars = carResponse
                    val newCars = resultResponse
                    oldCars?.addAll(newCars)

                }
               // Log.d("response", response.body().toString())
                return Resource.Success(carResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}