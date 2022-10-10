package com.example.carapi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.carapi.CarApplication
import com.example.carapi.R
import com.example.carapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarFragment : Fragment() {


    private val viewModel by activityViewModels<CarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.cars.observe(viewLifecycleOwner, Observer{ response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { carResponse ->
                        Log.d("witam", carResponse[43].model)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message ->
                        Log.e("witamerror", "an error occuerd: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }

        })



        return inflater.inflate(R.layout.fragment_car, container, false)
    }


}