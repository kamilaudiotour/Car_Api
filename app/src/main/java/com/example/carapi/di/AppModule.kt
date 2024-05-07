package com.example.carapi.di

import com.example.carapi.api.CarsApi
import com.example.carapi.api.MyInterceptor
import com.example.carapi.repository.banners.BannersRepository
import com.example.carapi.repository.banners.BannersRepositoryImpl
import com.example.carapi.repository.calculator.CalculatorRepository
import com.example.carapi.repository.calculator.CalculatorRepositoryImpl
import com.example.carapi.repository.calendar.CalendarRepository
import com.example.carapi.repository.calendar.CalendarRepositoryImpl
import com.example.carapi.repository.car.CarRepository
import com.example.carapi.repository.car.CarRepositoryImpl
import com.example.carapi.repository.dimensions.DimensionsRepository
import com.example.carapi.repository.dimensions.DimensionsRepositoryImpl
import com.example.carapi.repository.login.LoginRepository
import com.example.carapi.repository.login.LoginRepositoryImpl
import com.example.carapi.repository.order.CreateOrderRepository
import com.example.carapi.repository.order.CreateOrderRepositoryImpl
import com.example.carapi.repository.profile.ProfileRepository
import com.example.carapi.repository.profile.ProfileRepositoryImpl
import com.example.carapi.repository.project.ProjectRepository
import com.example.carapi.repository.project.ProjectRepositoryImpl
import com.example.carapi.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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
    fun providesLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun providesInterceptor(): MyInterceptor = MyInterceptor()

    @Provides
    @Singleton
    fun provideClient(logging: HttpLoggingInterceptor, interceptor: MyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .build()


    @Provides
    @Singleton
    fun provideCarRepository(api: CarsApi, db: FirebaseFirestore, auth: FirebaseAuth): CarRepository = CarRepositoryImpl(api,db, auth)


    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(firebaseAuth)


    @Provides
    @Singleton
    fun provideBannerRepository(): BannersRepository = BannersRepositoryImpl()

    @Provides
    @Singleton
    fun providesProfileRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): ProfileRepository =
        ProfileRepositoryImpl(firebaseFirestore, firebaseAuth)


    @Provides
    @Singleton
    fun providesProjectRepository(
        db: FirebaseFirestore,
        storage: FirebaseStorage,
        auth: FirebaseAuth
    ): ProjectRepository = ProjectRepositoryImpl(db, storage, auth)

    @Provides
    @Singleton
    fun providesCalculatorRepository(db: FirebaseFirestore, auth: FirebaseAuth): CalculatorRepository =
        CalculatorRepositoryImpl(db,auth)

    @Provides
    @Singleton
    fun providesDimensionsRepository(db: FirebaseFirestore): DimensionsRepository =
        DimensionsRepositoryImpl(db)

    @Provides
    @Singleton
    fun providesCalendarRepository(db: FirebaseFirestore) : CalendarRepository = CalendarRepositoryImpl(db)
    @Provides
    @Singleton
    fun providesCreateOrderRepository(db:FirebaseFirestore, auth: FirebaseAuth): CreateOrderRepository = CreateOrderRepositoryImpl(db,auth)
}
