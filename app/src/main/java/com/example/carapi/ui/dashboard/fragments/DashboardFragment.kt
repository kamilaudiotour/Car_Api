package com.example.carapi.ui.dashboard.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.carapi.R
import com.example.carapi.adapter.BannerListener
import com.example.carapi.adapter.ViewPagerAdapter
import com.example.carapi.databinding.FragmentDashboardBinding
import com.example.carapi.ui.dashboard.BannersViewModel
import com.example.carapi.util.ConnectivityObserver
import com.example.carapi.util.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel by activityViewModels<BannersViewModel>()
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        //set bottom nav bar visible
        showBottomNavBar()


        // setup clicklisteners to navigate to different fragments
        handleDashboardNavActions()

        //load list of banner promotions from folia-samochodowa.pl and attach it to list adapter + circle indicator setup
        checkNetworkAndLoadData()
        setupViewPager()

        return binding.root
    }


    private fun showBottomNavBar() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.VISIBLE
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

    private fun checkNetworkAndLoadData() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityObserver = NetworkConnectivityObserver(requireContext())
        connectivityObserver.observe().onStart {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities == null || !networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                )
            ) {
                emit(ConnectivityObserver.Status.Unavailable)
            } else {
                emit(ConnectivityObserver.Status.Available)
            }
        }.onEach {
            when (it) {
                ConnectivityObserver.Status.Available -> {
                    loadData()
                    networkWorkingComponents()
                }
                ConnectivityObserver.Status.Lost -> {
                    noNetworkComponents()
                    noNetworkComponents()
                }
                ConnectivityObserver.Status.Losing -> {
                    networkWorkingComponents()
                }
                ConnectivityObserver.Status.Unavailable -> {
                    noNetworkComponents()
                }
            }
        }.launchIn(lifecycleScope)
    }


    private fun handleDashboardNavActions() {
        binding.apply {
            dimensionCv.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_dimensionsFragment)
            }
            projectsCv.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_projectsFragment)
            }
            aboutCv.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_aboutUsFragment)
            }
            createCv.setOnClickListener{
                findNavController().navigate(R.id.action_dashboardFragment_to_createOrderFragment)
            }
            calculatorCv.setOnClickListener {
                findNavController().navigate(R.id.action_dashboardFragment_to_calculatorFragment)
            }
            calendarCv.setOnClickListener {
                findNavController().navigate((R.id.action_dashboardFragment_to_calendarFragment))
            }
        }
    }

    private fun noNetworkComponents(){
        binding.apply {
            viewPager.visibility = View.GONE
            indicator.visibility = View.GONE
            noNetworkTv.visibility = View.VISIBLE

        }
    }

    private fun networkWorkingComponents(){
        binding.apply {
            viewPager.visibility = View.VISIBLE
            indicator.visibility = View.VISIBLE
            noNetworkTv.visibility = View.GONE
        }
    }
}
