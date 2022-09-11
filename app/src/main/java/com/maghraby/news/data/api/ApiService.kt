package com.maghraby.news.data.api

import com.maghraby.news.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("news")
    suspend fun getUsers(
        @Query("access_key") apiKey: String,
    ): Response<BaseResponse>
}