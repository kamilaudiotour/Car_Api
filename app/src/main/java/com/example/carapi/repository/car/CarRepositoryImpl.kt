package com.example.carapi.repository.car

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.carapi.paging.CarPagingSource
import com.example.carapi.api.CarsApi
import com.example.carapi.models.Car
import retrofit2.Response
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val api: CarsApi) : CarRepository {

    override suspend fun getCarsMakes(
    ): Response<MutableList<String>> {
        return api.getCarsMakes()
    }

    override suspend fun getCarsModels(
        limit: String,
        page: String,
        make: String,
        model: String
    ): Response<MutableList<Car>> {
        return api.getCarsModels(limit, page, make, model)
    }


    override fun carPagingSource(make: String, model: String) = Pager(config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false,
    ),
        pagingSourceFactory = { CarPagingSource(api, make, model) }
    ).liveData

    companion object {
        val NETWORK_PAGE_SIZE = 10
    }
}