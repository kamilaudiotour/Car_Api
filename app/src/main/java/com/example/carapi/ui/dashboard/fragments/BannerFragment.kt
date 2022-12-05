package com.example.carapi.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carapi.R
import com.example.carapi.databinding.FragmentBannerBinding

class BannerFragment : Fragment(R.layout.fragment_banner) {
    private lateinit var binding : FragmentBannerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater,container,false)

        binding.apply {

        }

        return binding.root
    }
}