package com.example.carapi.ui.car.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.carapi.R
import com.example.carapi.adapter.CarModelClickListener
import com.example.carapi.adapter.CarPagedAdapter
import com.example.carapi.databinding.FragmentCarModelsBinding
import com.example.carapi.ui.car.CarViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarModelsFragment : Fragment(R.layout.fragment_car_models) {
    private lateinit var binding: FragmentCarModelsBinding
    private lateinit var adapter: CarPagedAdapter
    private val viewModel by activityViewModels<CarViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarModelsBinding.inflate(inflater, container, false)


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
                        if (newText == ""){
                            viewModel.searchModel("")
                            binding.noResultsTv.visibility = View.GONE
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerView()
        return binding.root
    }


    private fun setupRecyclerView() {
        adapter = CarPagedAdapter(CarModelClickListener { car ->
            viewModel.onCarModelYearTypeClicked(car)
            viewModel.saveCar(car)
            findNavController().navigate(R.id.action_carModelsFragment_to_profileFragment)
        })

        handleLoadingState(adapter)

        binding.apply {
            carRv.setHasFixedSize(true)
            carRv.adapter = adapter
        }
        viewModel.listData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    private fun handleLoadingState(adapter: CarPagedAdapter) {
        binding.noResultsTv.visibility = View.INVISIBLE
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.append.endOfPaginationReached) {
                    if (adapter.itemCount < 1) {
                        binding.carRv.visibility = View.GONE
                        binding.noResultsTv.visibility = View.VISIBLE
                    } else{
                        binding.noResultsTv.visibility = View.GONE
                    }
                }
                binding.modelsPb.isVisible = loadStates.refresh is LoadState.Loading
                binding.carRv.isVisible = loadStates.refresh is LoadState.NotLoading
                if (loadStates.refresh is LoadState.Error) {
                    Toast.makeText(requireContext(), " EROR", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}


