package com.example.carapi.repository.banners

import com.example.carapi.models.Banner

interface BannersRepository {

    fun getBanners(): MutableList<Banner>
}