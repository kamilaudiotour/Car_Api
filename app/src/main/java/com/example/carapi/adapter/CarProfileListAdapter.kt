package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.adapter.CarProfileListAdapter.CarProfileViewHolder
import com.example.carapi.databinding.ItemCarProfileBinding
import com.example.carapi.models.Car

class CarProfileListAdapter(private val clickListener: CarProfileClickListener) :
    ListAdapter<Car, CarProfileViewHolder>(DiffCallback) {

    inner class CarProfileViewHolder(private var binding: ItemCarProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: CarProfileClickListener, car: Car) {
            binding.car = car
            binding.clickListener = clickListener

            binding.apply {
                makeTv.text = car.make
                modelTv.text = car.model
                typeTv.text = car.type
                yearTv.text = car.year
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarProfileViewHolder {
        return CarProfileViewHolder(
            ItemCarProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarProfileViewHolder, position: Int) {
        val car = getItem(position)
        holder.bind(clickListener, car)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }

    }

}

class CarProfileClickListener(val clickListener: (car: Car) -> Unit) {
    fun onClick(car: Car) = clickListener(car)
}