package com.example.carapi.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.databinding.FragmentResetPasswordBinding
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.util.Resource

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel by activityViewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        binding.apply {
            resetBtn.setOnClickListener {
                val email = emailEt.text.toString().trim() { it <= ' ' }
                viewModel.resetPassword(email)
                if (email.isEmpty()) {
                    Toast.makeText(requireContext(), "Najpierw wpisz swój adres e-mail!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.reset.observe(viewLifecycleOwner){
                        when (it) {
                            is Resource.Success -> {
                                Toast.makeText(requireContext(), "Wysłano pomyślnie!", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Error ->  {
                                Toast.makeText(requireContext(), "Wystąpił błąd", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Loading -> {

                            }
                            null -> {}
                        }
                    }
                }
            }
        }



        return binding.root
    }
}