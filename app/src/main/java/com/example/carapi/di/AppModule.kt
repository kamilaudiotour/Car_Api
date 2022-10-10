package com.example.carapi.di

import com.example.carapi.api.CarsApi
import com.example.carapi.api.MyInterceptor
import com.example.carapi.repository.CarRepository
import com.example.carapi.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCarsApi() : CarsApi {
         val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(MyInterceptor())
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api  by lazy {
            retrofit.create(CarsApi::class.java)
        }

        return api
    }

    @Provides
    @Singleton
    fun provideCarRepository(api: CarsApi): CarRepository{
        return CarRepository(api)
    }


}