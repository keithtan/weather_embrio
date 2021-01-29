package com.example.weatherembrio.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherembrio.databinding.ListItemForecastBinding
import com.example.weatherembrio.db.ForecastItem

class ForecastItemAdapter : ListAdapter<ForecastItem, ForecastItemAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemForecastBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cast = getItem(position)
        cast?.let {
            holder.bind(it)
        }
    }

    class ItemViewHolder(private val binding: ListItemForecastBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastItem: ForecastItem) {
            binding.forecastItem = forecastItem
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
        override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
            return oldItem == newItem
        }
    }

}