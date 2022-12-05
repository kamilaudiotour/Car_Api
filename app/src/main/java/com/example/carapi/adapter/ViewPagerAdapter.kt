package com.example.carapi.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
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

class ViewPagerAdapter(private var listData: List<Banner>) :
    RecyclerView.Adapter<ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(private var binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner) {

            Picasso.get().load(banner.img).resize(140, 140).into(binding.itemImg)
            binding.oldPriceTv.text = banner.oldPrice
            binding.oldPriceTv.paintFlags =
                binding.oldPriceTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.newPriceTv.text = banner.newPrice
            binding.itemTitleTv.text = banner.text
            val numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN) as DecimalFormat
            val oldPrice = numberFormat.parse(banner.oldPrice)?.toDouble()
            val newPrice = numberFormat.parse(banner.newPrice)?.toDouble()
            val discount = (1.minus(newPrice?.div(oldPrice!!)!!)).times(100).toBigDecimal()
                .setScale(0, RoundingMode.HALF_UP).toInt()
            val dsc = binding.root.context.getString(R.string.discount_tv, discount)
            binding.discountTv.text = dsc

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
        val currentBanner = listData[position]
        holder.bind(currentBanner)
        setOnItemClickListener {
            onItemClickListener?.let {

            }
        }
    }

    override fun getItemCount() = listData.size

    private var onItemClickListener: ((Banner) -> Unit)? = null

    fun setOnItemClickListener(listener: (Banner) -> Unit){
        onItemClickListener = listener
    }

}


