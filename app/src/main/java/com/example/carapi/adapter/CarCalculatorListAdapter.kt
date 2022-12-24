package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.adapter.CarCalculatorListAdapter.CarCalculatorViewHolder
import com.example.carapi.databinding.ItemCarCalculatorBinding
import com.example.carapi.models.Car

class CarCalculatorListAdapter(
    private val clickListener: CarCalculatorClickListener,
) :
    ListAdapter<Car, CarCalculatorViewHolder>(DiffCallback) {

    inner class CarCalculatorViewHolder(private var binding: ItemCarCalculatorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: CarCalculatorClickListener, car: Car) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarCalculatorViewHolder {
        return CarCalculatorViewHolder(
            ItemCarCalculatorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarCalculatorViewHolder, position: Int) {
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

class CarCalculatorClickListener(val clickListener: (car: Car) -> Unit) {
    fun onClick(car: Car) = clickListener(car)
}
