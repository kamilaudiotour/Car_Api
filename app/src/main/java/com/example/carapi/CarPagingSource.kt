package com.example.carapi

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.carapi.api.CarsApi
import com.example.carapi.models.Car
import com.example.carapi.repository.CarRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.delay
import retrofit2.HttpException
import javax.inject.Inject


private const val CAR_MODELS_STARTING_PAGE = 0

class CarPagingSource @Inject constructor(
    private val carsApi: CarsApi,
    private val make: String,
    private val model: String
) :
    PagingSource<Int, Car>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Car> {
        val currentPage = params.key ?: CAR_MODELS_STARTING_PAGE
        return try {
            delay(1000)
            val response = carsApi.getCarsModels(
                limit = params.loadSize.toString(),
                page = currentPage.toString(),
                make,
                model
            )
            val data = response.body() ?: emptyList()
            val responseData = mutableListOf<Car>()
            responseData.addAll(data)


            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == CAR_MODELS_STARTING_PAGE) null else -1,
                nextKey = if (data.isEmpty()) null else currentPage + (params.loadSize / NETWORK_PAGE_SIZE)
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Car>): Int? {
        return null
    }


}