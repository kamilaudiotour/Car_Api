package com.example.carapi.ui.car.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.CarMakeClickListener
import com.example.carapi.adapter.CarMakesListAdapter
import com.example.carapi.databinding.FragmentCarMakesBinding
import com.example.carapi.ui.car.CarViewModel
import com.example.carapi.util.ConnectivityObserver
import com.example.carapi.util.NetworkConnectivityObserver
import com.example.carapi.util.Resource
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@AndroidEntryPoint
class CarMakesFragment : Fragment(R.layout.fragment_car_makes) {

    private lateinit var binding: FragmentCarMakesBinding
    private lateinit var carMakesAdapter: CarMakesListAdapter
    private val viewModel by activityViewModels<CarViewModel>()
    private lateinit var connectivityObserver: ConnectivityObserver


    private val TAG = "CAR MODELS FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarMakesBinding.inflate(inflater, container, false)

        hideBottomNav()

        // continuously check for network statuts and if network is available load Car Makes List
        checkNetworkAndLoadData()
        //Setup Recycler View and onItemClickListener
        setupRv()

        return binding.root
    }


    private fun loadingCarMakesData() {
        viewModel.carMakes.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    readyComponents()
                    response.data?.let { carMakesResponse ->
                        carMakesAdapter.submitList(carMakesResponse.sorted())
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "Error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    loadingComponents()
                }
            }
        }

    }

    private fun setupRv() {
        carMakesAdapter = CarMakesListAdapter(CarMakeClickListener { make ->
            viewModel.onCarMakeClicked(make)
            findNavController()
                .navigate(R.id.action_carFragment_to_carModelsFragment)
        })
        binding.carRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = carMakesAdapter
            setHasFixedSize(true)
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
                    networkWorkingComponents()
                    loadingCarMakesData()
                }
                ConnectivityObserver.Status.Lost -> {
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

    private fun loadingComponents() {
        binding.apply {
            makesPb.visibility = View.VISIBLE
            carRv.visibility = View.INVISIBLE
        }
    }

    private fun readyComponents() {
        binding.apply {
            makesPb.visibility = View.INVISIBLE
            carRv.visibility = View.VISIBLE
        }
    }

    private fun noNetworkComponents() {
        binding.apply {
            carRv.visibility = View.GONE
            makesPb.visibility = View.GONE
            noNetworkIv.visibility = View.VISIBLE
            noNetworkTv.visibility = View.VISIBLE
        }
    }

    private fun networkWorkingComponents() {
        binding.apply {
            noNetworkIv.visibility = View.GONE
            noNetworkTv.visibility = View.GONE
        }
    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }


}

