package com.adesire.kmmdemo.kmmDemoAndroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adesire.kmmdemo.kmmDemoAndroid.databinding.ItemRestaurantBinding
import com.adesire.kmmdemo.kmmDemoAndroid.model.RestaurantModel
import com.adesire.kmmdemo.shared.Restaurant

class RestaurantAdapter :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    var c: Context? = null
    private var listener: OnRestaurantClickedListener? = null
    private var items: List<Restaurant>? = null

    fun setItems(items: List<Restaurant>) {
        this.items = items
    }

    fun setClickListener(listener: OnRestaurantClickedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val model = items!![position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class RestaurantViewHolder(var binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(model: Restaurant) {
            binding.restaurant = RestaurantModel(
                model.uid,
                model.name,
                model.description,
                model.type,
                model.logo,
                model.review
            )
        }
    }

    interface OnRestaurantClickedListener {
        fun onAtcClick(model: Restaurant, section: String?)
    }
}