package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.R
import com.example.carapi.adapter.DimensionsListAdapter.*
import com.example.carapi.databinding.ItemMeasurementBinding
import com.example.carapi.models.Measurement

class DimensionsListAdapter() : ListAdapter<Measurement, DimensionsViewHolder>(DiffCallback) {

    inner class DimensionsViewHolder(private var binding: ItemMeasurementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(measurement: Measurement) {
            binding.apply {
                measurementResultTv.text =
                    root.context.getString(R.string.result_tv, measurement.size.toDouble())
                byTv.text = root.context.getString(R.string.by_tv, measurement.by)
                makeTv.text = measurement.car.make
                modelTv.text = measurement.car.model
                yearTv.text = measurement.car.year
                typeTv.text = measurement.car.type

            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DimensionsViewHolder {
        return DimensionsViewHolder(
            ItemMeasurementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DimensionsViewHolder, position: Int) {
        val measurement = getItem(position)
        holder.bind(measurement)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Measurement>() {
        override fun areItemsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
            return oldItem == newItem
        }

    }
}