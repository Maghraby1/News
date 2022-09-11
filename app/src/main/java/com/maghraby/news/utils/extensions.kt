package com.maghraby.news.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.maghraby.news.R

fun ImageView.setImage(image_url: String?=""){
    Glide.with(this.context)
        .load(image_url)
        .placeholder(R.drawable.ic_no_image)
        .into(this)
}