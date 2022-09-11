package com.maghraby.news.data.api

import com.maghraby.news.data.model.BaseResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getNews(offset: Int): Response<BaseResponse>
}