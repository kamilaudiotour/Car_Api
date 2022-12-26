package com.example.carapi.ui.dimensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Measurement
import com.example.carapi.repository.dimensions.DimensionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DimensionsViewModel @Inject constructor(private val dimensionsRepository: DimensionsRepository) :
    ViewModel() {

    private val _measurement = MutableLiveData<List<Measurement>>()
    val measurement: LiveData<List<Measurement>>
        get() = _measurement

    init {
        getMeasurement()
    }


    fun getMeasurement() {
        viewModelScope.launch {
            _measurement.value = dimensionsRepository.getMeasurement()
        }
    }

}