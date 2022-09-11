package com.maghraby.news.data.model

data class Pagination (
        val limit: Int,
        val offset: Int,
        val count: Int,
        val total: Int,
        )
