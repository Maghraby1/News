package com.maghraby.news.data.model

data class BaseResponse(
//    val status: String,
//    val totalResults : Int,
//    val results: List<News>,
    val data: List<News>,
    val pagination: Pagination
)