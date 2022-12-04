package com.example.carapi.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.models.Banner
import com.example.carapi.repository.banners.BannersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BannersViewModel @Inject constructor(private val bannersRepository: BannersRepository) :
    ViewModel() {

    var banners : MutableLiveData<MutableList<Banner>> = MutableLiveData()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(IO) {
            banners.postValue(bannersRepository.getBanners())
        }
    }
}