package com.example.carapi.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carapi.R
import com.example.carapi.databinding.FragmentCreateOrderBinding

class CreateOrderFragment : Fragment(R.layout.fragment_create_order) {

    private lateinit var binding: FragmentCreateOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateOrderBinding.inflate(inflater, container, false)

        return binding.root
    }
}