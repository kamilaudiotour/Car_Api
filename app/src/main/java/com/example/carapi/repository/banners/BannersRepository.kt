package com.example.carapi.repository.banners

import android.util.Log
import com.example.carapi.models.Banner
import org.jsoup.Jsoup

interface BannersRepository {

    fun getBanners(): MutableList<Banner>
}