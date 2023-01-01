package com.example.carapi.ui.profile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.CarProfileClickListener
import com.example.carapi.adapter.CarProfileListAdapter
import com.example.carapi.adapter.OrderListAdapter
import com.example.carapi.databinding.FragmentProfileBinding
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.ui.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var carProfileAdapter: CarProfileListAdapter
    private lateinit var orderAdapter: OrderListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        //show bottom nav bar if there was a fragment that hided it before
        showBottomNavBar()

        binding.apply {

            logoutBtn.setOnClickListener {
                loginViewModel.logout()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }

            addCarBtn.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_carFragment)
            }
            profileNameTv.text = loginViewModel.currentUser?.displayName
            profileEmailTv.text = loginViewModel.currentUser?.email.toString()
        }

        // initial load of user's car database
        profileViewModel.readCarsData()

        profileViewModel.getOrdersByUser()


        // load list of cars to recycler view
        setupCarRv()
        loadCarData()

        setupOrderRv()
        loadOrderData()





        return binding.root
    }
    private fun setupCarRv() {
        carProfileAdapter = CarProfileListAdapter(CarProfileClickListener {
            profileViewModel.deleteCar(it)
        })
        binding.profileCarsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carProfileAdapter
        }
    }

    private fun loadCarData() {
        profileViewModel.profileCars.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noCarsTv.visibility = View.VISIBLE
                binding.profileCarsRv.visibility = View.GONE
            } else {
                binding.noCarsTv.visibility = View.GONE
                binding.profileCarsRv.visibility = View.VISIBLE
                profileViewModel.readCarsData()
                carProfileAdapter.submitList(it)
            }


        }
    }

    private fun setupOrderRv() {
        orderAdapter = OrderListAdapter()
        binding.ordersCarsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = orderAdapter
        }
    }

    private fun loadOrderData() {
        profileViewModel.ordersByUser.observe(viewLifecycleOwner) {
            Log.d("profile order", it.toString())
            if (it.isEmpty()) {
                binding.noOrdersTv.visibility = View.VISIBLE
                binding.ordersCarsRv.visibility = View.GONE
            } else {
                binding.noOrdersTv.visibility = View.GONE
                binding.ordersCarsRv.visibility = View.VISIBLE
                profileViewModel.getOrdersByUser()
                orderAdapter.submitList(it)
            }


        }
    }



    private fun showBottomNavBar() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.VISIBLE
    }


}