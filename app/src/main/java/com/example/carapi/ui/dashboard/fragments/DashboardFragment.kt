package com.example.carapi.ui.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.carapi.R
import com.example.carapi.adapter.BannerListener
import com.example.carapi.adapter.ViewPagerAdapter
import com.example.carapi.databinding.FragmentDashboardBinding
import com.example.carapi.ui.dashboard.BannersViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by activityViewModels<BannersViewModel>()
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)


        loadData()
        setupViewPager()

        return binding.root
    }


    private fun setupViewPager() {
        adapter = ViewPagerAdapter(BannerListener { banner ->
            val bundle = Bundle().apply {
                putSerializable("link", banner.link)
            }
            findNavController().navigate(R.id.action_dashboardFragment_to_bannerFragment, bundle)
        })
        viewPager = binding.viewPager
        viewPager.adapter = adapter
    }

    private fun loadData() {
        viewModel.banners.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.indicator.setViewPager(viewPager)
        }
    }
}
