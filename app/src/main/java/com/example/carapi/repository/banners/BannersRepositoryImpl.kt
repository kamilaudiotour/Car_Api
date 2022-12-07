package com.example.carapi.repository.banners

import android.util.Log
import com.example.carapi.models.Banner
import org.jsoup.Jsoup

class BannersRepositoryImpl : BannersRepository {
    override fun getBanners(): MutableList<Banner> {

        val listData = mutableListOf<Banner>()
        try {

            val url = "https://folia-samochodowa.pl/"
            val doc = Jsoup.connect(url).get()
            val banners = doc.select("#box_specialoffer .innerbox .product.center.row")

            val bannersSize = banners.size

            for (i in 0 until bannersSize) {
                val text = banners.select("span.productname").eq(i).text()
                val imgUrl = url + banners.select(".spanhover img").eq((2 * i) + 1).attr("src")
                val oldPrice = banners.select(".price.row del").eq((i * 2)).text()
                val newPrice = banners.select(".price.row em").eq((i * 2)).text()
                val link = url + banners.select("a").eq(i).attr("href")
                Log.d("folia link", link.toString())
                val discount = banners.select("a span ").eq(i).text()
                Log.d("siemaada", discount.toString())
                listData.add(Banner(i, text, imgUrl, newPrice, oldPrice, link))

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return listData

    }
}