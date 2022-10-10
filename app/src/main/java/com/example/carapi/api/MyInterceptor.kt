package com.example.carapi.api

import com.example.carapi.util.Constants.Companion.API_HOST
import com.example.carapi.util.Constants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()
        return chain.proceed(request)
    }
}