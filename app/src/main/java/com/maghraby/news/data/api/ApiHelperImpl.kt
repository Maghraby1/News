package com.maghraby.news.data.api

import com.maghraby.news.data.model.BaseResponse
import com.maghraby.news.utils.Constants.ACCESS_KEY
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNews(offset: Int): Response<BaseResponse> =
        apiService.getNews(apiKey = ACCESS_KEY, limit = 25,offset = offset)

    override suspend fun getNews(offset: Int, countries: String) =
        apiService.getNews(apiKey = ACCESS_KEY, limit = 25,offset = offset, countries = countries)
}