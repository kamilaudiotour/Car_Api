package com.example.carapi.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.R
import com.example.carapi.adapter.ViewPagerAdapter.ViewPagerViewHolder
import com.example.carapi.databinding.ItemBannerBinding
import com.example.carapi.models.Banner
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ViewPagerAdapter(private val clickListener : BannerListener) :
    ListAdapter<Banner, ViewPagerViewHolder>(DiffCallback){


    inner class ViewPagerViewHolder(private var binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner, clickListener: BannerListener) {

            binding.banner = banner
            binding.clickListener = clickListener


            binding.apply {
                Picasso.get().load(banner.img).resize(140, 140).into(itemImg)
                oldPriceTv.text = banner.oldPrice
                oldPriceTv.paintFlags =
                    oldPriceTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
               newPriceTv.text = banner.newPrice
                itemTitleTv.text = banner.text
                val numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN) as DecimalFormat
                val oldPrice = numberFormat.parse(banner.oldPrice)?.toDouble()
                val newPrice = numberFormat.parse(banner.newPrice)?.toDouble()
                val discount = (1.minus(newPrice?.div(oldPrice!!)!!)).times(100).toBigDecimal()
                    .setScale(0, RoundingMode.HALF_UP).toInt()
                val dsc = root.context.getString(R.string.discount_tv, discount)
                discountTv.text = dsc
            }
            binding.executePendingBindings()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentBanner = getItem(position)
        holder.bind(currentBanner,clickListener)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

    }



}
class BannerListener(val clickListener: (banner: Banner) -> Unit) {
    fun onClick(banner: Banner) = clickListener(banner)
}


