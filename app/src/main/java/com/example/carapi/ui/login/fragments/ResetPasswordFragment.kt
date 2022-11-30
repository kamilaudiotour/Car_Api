package com.example.carapi.ui.login.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carapi.R
import com.example.carapi.databinding.FragmentResetPasswordBinding
import com.example.carapi.ui.login.LoginViewModel

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
                if (email.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Najpierw wpisz swój adres email!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.resetPassword(email, requireContext())
                }
            }
            haveAccountTv.setOnClickListener {
                findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
            }
        }




        return binding.root
    }
}