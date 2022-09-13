package com.maghraby.news.ui.main.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.*
import android.view.View.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maghraby.news.ui.main.adapter.model.News
import com.maghraby.news.databinding.NewsLayoutBinding
import com.maghraby.news.utils.setImage



class NewsAdapter(
    private val news: ArrayList<News>,
    private val clickListener : (News) -> Unit
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
    private fun rotate(iv: ImageView, value: Float){
        iv.apply {
            animate().rotation(value).setDuration(600)
        }
    }
    private fun toggleVisibility(tv: TextView, show: Boolean) {
        if(show){
            tv.apply {
                alpha = 0f
                visibility = VISIBLE
            }
            tv.animate().apply {
                alpha(1f)
                duration = 600
                setListener(null)
            }
        }else{
            tv.animate().apply {
                alpha(0f)
                duration = 600
                setListener(object  : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        tv.visibility = GONE
                    }
                })

            }
        }
    }
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        if(news[position].isExpanded){
            holder.binding.newsDetailsTV.visibility = VISIBLE
            holder.binding.moreButton.rotation = 180f
        }else{
            holder.binding.newsDetailsTV.visibility = GONE
            holder.binding.moreButton.rotation = 0f
        }
        holder.binding.newsTitleTV.text = news[position].title
        holder.binding.newsDetailsTV.text = news[position].description
        holder.binding.newsIV.setImage((news[position].image))
        holder.binding.moreButton.setOnClickListener {
            news[position].isExpanded = !news[position].isExpanded
            toggleVisibility(holder.binding.newsDetailsTV,news[position].isExpanded)
            rotate(holder.binding.moreButton,if(news[position].isExpanded) 180f else 0f)
        }

        
        holder.binding.root.setOnClickListener {
            clickListener(news[position])
        }
    }

    fun addData(list: List<News>,newsData :Boolean = false) {
        if(newsData){
            news.clear()
        }
        news.addAll(list)
    }
}
