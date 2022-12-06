package com.example.carapi.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.carapi.R
import com.example.carapi.databinding.FragmentBannerBinding

class BannerFragment : Fragment(R.layout.fragment_banner) {

    private lateinit var binding: FragmentBannerBinding

    val args: BannerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater, container, false)

        val url = args.link
        binding.bannerWv.apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }

        return binding.root
    }
}