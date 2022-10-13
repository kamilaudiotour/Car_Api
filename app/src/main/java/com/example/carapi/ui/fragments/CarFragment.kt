package com.example.carapi.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.carapi.R
import com.example.carapi.ui.CarViewModel
import com.example.carapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarFragment : Fragment() {


    private val viewModel by activityViewModels<CarViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.cars.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { carResponse ->
                        Log.d("response", carResponse[43].model)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("response error", "an error occuerd: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }

        }



        return inflater.inflate(R.layout.fragment_car, container, false)
    }


}