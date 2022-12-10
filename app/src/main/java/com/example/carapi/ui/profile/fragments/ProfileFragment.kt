package com.example.carapi.ui.profile.fragments

import android.os.Bundle
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
import com.example.carapi.databinding.FragmentProfileBinding
import com.example.carapi.ui.car.CarViewModel
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.ui.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val carViewModel: CarViewModel by activityViewModels()
    private lateinit var carProfileAdapter: CarProfileListAdapter

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
        // get currently logged in user id
        val userId = loginViewModel.currentUser?.uid.toString()

        // load user's cars database
        loadData(userId)
        setupRv(userId)

        checkForCarToAdd(userId)

        // initial load of user's car database
        profileViewModel.readCarsData(userId)

        // check if there is an argument from car make and models fragment and pass it to firebase
        // checkForCarToAdd(userId)


        return binding.root
    }

    private fun checkForCarToAdd(userId: String) {
        val car = carViewModel.selectedCar.value
        if (car != null) {
            profileViewModel.saveCar(userId, car)
            carViewModel.afterCarAdded()
        }
    }

    private fun loadData(userId: String) {
        profileViewModel.profileCars.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noCarsTv.visibility = View.VISIBLE
                binding.profileCarsRv.visibility = View.GONE
            } else {
                binding.noCarsTv.visibility = View.GONE
                binding.profileCarsRv.visibility = View.VISIBLE
                profileViewModel.readCarsData(userId)
                carProfileAdapter.submitList(it)
            }


        }
    }

    private fun setupRv(userId: String) {
        carProfileAdapter = CarProfileListAdapter(CarProfileClickListener {
            profileViewModel.deleteCar(userId, it)
        })
        binding.profileCarsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carProfileAdapter
        }
    }

    private fun showBottomNavBar() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.VISIBLE
    }


}