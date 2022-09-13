package com.maghraby.news.ui.main.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maghraby.news.R
import com.maghraby.news.databinding.CountryItemBinding
import com.maghraby.news.ui.main.adapter.model.Country
import com.maghraby.news.ui.main.adapter.model.News

class CountryAdapter(
    private val countries: ArrayList<Country>,
    private val selectedListener: (String) -> Unit,
    private val unselectedListener: (String) -> Unit,
) : RecyclerView.Adapter<CountryAdapter.DataViewHolder>() {
    inner class DataViewHolder(val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = countries.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.countryNameTV.text = countries[holder.adapterPosition].name
        if (countries[holder.adapterPosition].isSelected) {
            holder.binding.root.setCardBackgroundColor(Color.DKGRAY)
            holder.binding.countryNameTV.setTextColor(Color.WHITE)
        } else {
            holder.binding.root.setCardBackgroundColor(Color.LTGRAY)
            holder.binding.countryNameTV.setTextColor(Color.BLACK)
        }
        holder.binding.root.setOnClickListener {
            countries[holder.adapterPosition].isSelected =
                !countries[holder.adapterPosition].isSelected

            if (countries[holder.adapterPosition].isSelected) {
                holder.binding.root.setCardBackgroundColor(Color.DKGRAY)
                holder.binding.countryNameTV.setTextColor(Color.WHITE)
                selectedListener(countries[holder.adapterPosition].abb)
            } else {
                holder.binding.root.setCardBackgroundColor(Color.LTGRAY)
                holder.binding.countryNameTV.setTextColor(Color.BLACK)
                unselectedListener(countries[holder.adapterPosition].abb)
            }

        }
    }

    fun addData(list: List<Country>) {
        countries.addAll(list)
    }
}