package com.example.carapi.ui.car

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.carapi.repository.car.CarRepository
import com.example.carapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CarViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    val carMakes: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    private val carMake = MutableLiveData("BMW")
    private val modelQuery = MutableLiveData("")
    private var carMakesResponse: MutableList<String>? = null

    init {
        getCarMakes()
    }

    val listData = carMake.switchMap { carMake ->
        modelQuery.switchMap { carModel ->
            carRepository.carPagingSource(carMake, carModel).cachedIn(viewModelScope)
        }

    }

    private fun getCarMakes() = viewModelScope.launch {
        carMakes.postValue(Resource.Loading())
        val response = carRepository.getCarsMakes()
        carMakes.postValue(handleCarMakesResponse(response))

    }

    private fun handleCarMakesResponse(response: Response<MutableList<String>>): Resource<List<String>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (carMakesResponse == null) {
                    carMakesResponse = resultResponse
                } else {
                    val oldCarMakes = carMakesResponse
                    oldCarMakes?.addAll(resultResponse)

                }
                return Resource.Success(carMakesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchModel(query: String) {
        modelQuery.value = query
    }

    fun onCarMakeClicked(make: String) {
        carMake.value = make
    }
}