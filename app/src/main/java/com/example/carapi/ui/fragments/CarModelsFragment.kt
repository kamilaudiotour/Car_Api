package com.example.carapi.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.carapi.R
import com.example.carapi.adapter.CarPagedAdapter
import com.example.carapi.databinding.FragmentCarModelsBinding
import com.example.carapi.ui.CarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarModelsFragment : Fragment(R.layout.fragment_car_models) {
    private var _binding: FragmentCarModelsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<CarViewModel>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCarModelsBinding.bind(view)
        setupRecyclerView()
// Search menu functionality
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_car_models, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            binding.carRv.scrollToPosition(0)
                            viewModel.searchModel(query)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun setupRecyclerView() {
        val adapter = CarPagedAdapter()
        binding.apply {
            carRv.setHasFixedSize(true)
            carRv.adapter = adapter
        }
        viewModel.listData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }



}


