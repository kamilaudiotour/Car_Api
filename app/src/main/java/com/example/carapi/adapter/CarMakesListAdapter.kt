package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.databinding.ItemCarMakeBinding

class CarMakesListAdapter(private val clickListener: CarMakeClickListener) :
    ListAdapter<String, CarMakesListAdapter.CarMakesViewHolder>(DiffCallback) {

    inner class CarMakesViewHolder(private var binding: ItemCarMakeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: CarMakeClickListener, make: String) {
            binding.makeTv.text = make
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarMakesViewHolder {
        return CarMakesViewHolder(
            ItemCarMakeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarMakesViewHolder, position: Int) {
        val make = getItem(position)
        holder.bind(clickListener, make)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

}

class CarMakeClickListener(val clickListener: (make: String) -> Unit) {
    fun onClick(make: String) = clickListener(make)
}
