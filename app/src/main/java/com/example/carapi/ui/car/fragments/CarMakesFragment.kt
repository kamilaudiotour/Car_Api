package com.example.carapi.ui.car.fragments

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
import com.example.carapi.adapter.CarMakeClickListener
import com.example.carapi.adapter.CarMakesListAdapter
import com.example.carapi.databinding.FragmentCarMakesBinding
import com.example.carapi.ui.car.CarViewModel
import com.example.carapi.ui.login.LoginViewModel
import com.example.carapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarMakesFragment : Fragment(R.layout.fragment_car_makes) {

    private lateinit var binding: FragmentCarMakesBinding
    private lateinit var carMakesAdapter: CarMakesListAdapter
    private val viewModel by activityViewModels<CarViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()

    private val TAG = "CAR MODELS FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarMakesBinding.inflate(inflater, container, false)

        binding.logoutBtn.setOnClickListener {
            loginViewModel.logout()
            //findNavController().navigate(R.id.action_carFragment_to_loginFragment)
        }

        loadingCarMakesData()
        setupCarMakesRecyclerView()

        return binding.root
    }

    private fun loadingCarMakesData() {
        viewModel.carMakes.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { carMakesResponse ->
                        carMakesAdapter.submitList(carMakesResponse.sorted())
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "error occured: $message")
                    }
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setupCarMakesRecyclerView() {
        carMakesAdapter = CarMakesListAdapter(CarMakeClickListener { make ->
            viewModel.onCarMakeClicked(make)
            findNavController()
                .navigate(R.id.action_carFragment_to_carModelsFragment)
        })
        binding.carRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = carMakesAdapter
            setHasFixedSize(true)
        }
    }


}