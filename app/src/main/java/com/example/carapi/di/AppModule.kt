package com.example.carapi.di

import com.example.carapi.api.CarsApi
import com.example.carapi.api.MyInterceptor
import com.example.carapi.repository.CarRepository
import com.example.carapi.repository.CarRepositoryImpl
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
    fun provideCarsApi(client: OkHttpClient): CarsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CarsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesLogging() : HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun providesInterceptor() : MyInterceptor = MyInterceptor()

    @Provides
    @Singleton
    fun provideClient(logging: HttpLoggingInterceptor, interceptor: MyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .build()


    @Provides
    @Singleton
    fun provideCarRepository(api: CarsApi): CarRepository {
        return CarRepositoryImpl(api)
    }


}