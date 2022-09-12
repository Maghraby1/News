package com.maghraby.news.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var isExpanded : Boolean = false,
    val author: String = "",
    val url: String = "",
    val title: String = "",
    val description: String = "",
    val published_at: String = "",
    val image: String = "",
    val source: String = "",
    val category: String,
    val country: String,
    val language: String = "",

//    val video_url: String = "",
//    val link: String = "",
//    val content: String = "",
//    val pubDate: String = "",
//    val full_description: String = "",
//    val image_url: String = "",
//    val source_id: String = "",
//    val creator: List<String>? = null,
//    val keywords: List<String>? = listOf(),
//    val category: List<String>,
//    val country: List<String>,
) : Parcelable
