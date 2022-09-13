package com.maghraby.news.data.repository

import com.maghraby.news.data.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getNews(offset: Int) =  apiHelper.getNews(offset)
    suspend fun getNews(offset: Int,countries : String) =  apiHelper.getNews(offset, countries)

}
