package com.example.carapi.ui.order.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.OrderListAdapter
import com.example.carapi.databinding.FragmentOrdersListDialogBinding
import com.example.carapi.ui.order.CreateOrderViewModel

class OrdersListDialogFragment : DialogFragment(R.layout.fragment_orders_list_dialog) {
    private lateinit var binding: FragmentOrdersListDialogBinding
    private val viewModel: CreateOrderViewModel by activityViewModels()
    private lateinit var orderAdapter: OrderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersListDialogBinding.inflate(inflater, container, false)


        setupOrderRv()
        loadOrderData()


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val background = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(background, 20)
        dialog?.window?.setBackgroundDrawable(inset)
        dialog?.window?.setLayout(
            (LinearLayout.LayoutParams.MATCH_PARENT),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

    }

    private fun setupOrderRv() {
        orderAdapter = OrderListAdapter()
        binding.ordersRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = orderAdapter
        }
    }

    private fun loadOrderData() {
        viewModel.getAllOrders()
        viewModel.allOrders.observe(viewLifecycleOwner) {
            Log.d("profile order", it.toString())
            if (it.isEmpty()) {
                binding.noOrdersTv.visibility = View.VISIBLE
                binding.ordersRv.visibility = View.GONE
            } else {
                binding.noOrdersTv.visibility = View.GONE
                binding.ordersRv.visibility = View.VISIBLE
                viewModel.allOrders
                orderAdapter.submitList(it)
            }
        }
    }

}