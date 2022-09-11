package com.maghraby.news.data.api

import com.maghraby.news.data.model.BaseResponse
import com.maghraby.news.utils.Constants.ACCESS_KEY
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<BaseResponse> =
        apiService.getUsers(apiKey = ACCESS_KEY)

}