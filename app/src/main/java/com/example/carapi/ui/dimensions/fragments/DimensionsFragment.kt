package com.example.carapi.ui.dimensions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.DimensionsListAdapter
import com.example.carapi.databinding.FragmentDimensionsBinding
import com.example.carapi.ui.dimensions.DimensionsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class DimensionsFragment : Fragment(R.layout.fragment_dimensions) {

    private lateinit var binding: FragmentDimensionsBinding
    private val viewModel: DimensionsViewModel by activityViewModels()
    private lateinit var dimensionsAdapter: DimensionsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDimensionsBinding.inflate(inflater, container, false)


        hideBottomNav()

        setupRv()
        loadData()



        return binding.root

    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }

    private fun setupRv() {
        dimensionsAdapter = DimensionsListAdapter()
        binding.dimensionsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = dimensionsAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadData() {
        viewModel.measurement.observe(viewLifecycleOwner) { measurement ->
            viewModel.getMeasurement()
            dimensionsAdapter.submitList(measurement)
        }
    }
}