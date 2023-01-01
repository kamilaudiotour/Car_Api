package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.R
import com.example.carapi.adapter.OrderListAdapter.OrderViewHolder
import com.example.carapi.databinding.ItemOrderBinding
import com.example.carapi.models.Order

class OrderListAdapter : ListAdapter<Order, OrderViewHolder>(DiffCallback) {

    inner class OrderViewHolder(private var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(order: Order) {

            binding.apply {
                modelTv.text = order.car.model
                makeTv.text = order.car.make
                yearTv.text = order.car.year
                typeTv.text = order.car.type
                nameSurnameTv.text = root.context.getString(R.string.ordered_by_tv, order.name)
                emailTv.text = root.context.getString(R.string.order_email_tv, order.email)
                remarksTv.text = root.context.getString(R.string.order_remarks_tv, order.remarks)
                phoneNumberTv.text =
                    root.context.getString(R.string.order_phone_number_tv, order.phone)
                foilVariantTv.text =
                    root.context.getString(R.string.order_foil_variant_tv, order.foilVariant)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        val order = getItem(position)
        holder.bind(order)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }

}