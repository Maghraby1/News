package com.maghraby.news.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maghraby.news.data.model.News
import com.maghraby.news.databinding.NewsLayoutBinding
import com.maghraby.news.utils.setImage

class NewsAdapter(
    private val news: ArrayList<News>,
    private val clickListener : (Int) -> Unit
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    inner class DataViewHolder(val binding: NewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            NewsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

//        if (news[holder.adapterPosition].creator.isNullOrEmpty()) {
//            holder.binding.textViewUserName.text = news[holder.adapterPosition].creator?.get(0)
//        }
        holder.binding.textViewUserName.text = news[holder.adapterPosition].author
        holder.binding.textViewUserEmail.text = news[holder.adapterPosition].description
        holder.binding.imageViewAvatar.setImage((news[holder.adapterPosition].image))
        holder.binding.root.setOnClickListener {
            clickListener(holder.adapterPosition)
        }

    }

    fun addData(list: List<News>) {
        news.addAll(list)
    }
}