package com.example.carapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.R
import com.example.carapi.adapter.ViewPagerAdapter.ViewPagerViewHolder
import com.example.carapi.databinding.ItemBannerBinding
import com.example.carapi.models.Banner

class ViewPagerAdapter(private var listData: List<Banner>) :
    RecyclerView.Adapter<ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(private var binding : ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(banner: Banner) {
                binding.itemImg.setImageResource(R.drawable.ic_calculator)
                binding.oldPriceTv.text = banner.oldPrice
                binding.newPriceTv.text = banner.newPrice
                binding.itemTitleTv.text = banner.text
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentBanner = listData[position]
        holder.bind(currentBanner)


    }


    override fun getItemCount(): Int {
        return listData.size
    }


}