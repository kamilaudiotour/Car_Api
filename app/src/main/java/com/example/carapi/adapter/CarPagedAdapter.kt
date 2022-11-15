package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.adapter.CarPagedAdapter.MyViewHolder
import com.example.carapi.databinding.ItemCarModelBinding
import com.example.carapi.models.Car

class CarPagedAdapter() :
    PagingDataAdapter<Car, MyViewHolder>(diffCallback) {

    inner class MyViewHolder(private val binding: ItemCarModelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car?) {
            binding.modelTv.text = car?.model
            binding.typeTv.text = car?.type
            binding.yearTv.text = car?.year
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val car = getItem(position)
        if (car != null) {
            holder.bind(car)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCarModelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

}

