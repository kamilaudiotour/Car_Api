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
import com.example.carapi.databinding.FragmentProfileBinding
import com.example.carapi.models.Car
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.ui.profile.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var carProfileAdapter : CarProfileListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.logoutBtn.setOnClickListener {
            loginViewModel.logout()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
        binding.addCarBtn.setOnClickListener {
            profileViewModel.saveCar("user1", Car(0, "przyklad", "przyklad", "przyklad", "przyklad"))
        }

        binding.profileNameTv.text = loginViewModel.currentUser?.displayName
        binding.profileEmailTv.text = loginViewModel.currentUser?.email.toString()
        Log.d("siemano user", loginViewModel.currentUser?.uid.toString())


        profileViewModel.readCarsData("user1")

        loadData()
        setupRv()

        return binding.root
    }

    private fun loadData(){
        profileViewModel.profileCars.observe(viewLifecycleOwner) {
            profileViewModel.readCarsData("user1")
            carProfileAdapter.submitList(it)
        }
    }

    fun setupRv(){
        carProfileAdapter = CarProfileListAdapter(CarProfileClickListener {

        })
        binding.profileCarsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carProfileAdapter
        }
    }


}