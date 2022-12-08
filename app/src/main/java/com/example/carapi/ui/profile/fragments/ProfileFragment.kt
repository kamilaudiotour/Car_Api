package com.example.carapi.ui.profile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.databinding.FragmentProfileBinding
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.ui.profile.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.profileNameTv.text = loginViewModel.currentUser?.displayName
        binding.profileEmailTv.text = loginViewModel.currentUser?.email.toString()
        Log.d("siemano user", loginViewModel.currentUser?.uid.toString())


        profileViewModel.readCarsData("user1")




        return binding.root
    }


}