package com.example.carapi.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.databinding.FragmentRegisterBinding
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.util.Resource

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.apply {
            registerBtn.setOnClickListener {
                val email: String = emailEt.text.toString()
                val name: String = nameEt.text.toString()
                val password: String = passwordEt.text.toString()
                viewModel.register(email, name, password)
            }
        }

        viewModel.register.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                else -> {}
            }
        }

        return binding.root
    }
}


