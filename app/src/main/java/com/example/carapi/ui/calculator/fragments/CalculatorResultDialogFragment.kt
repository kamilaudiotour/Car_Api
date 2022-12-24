package com.example.carapi.ui.calculator.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.CarCalculatorClickListener
import com.example.carapi.adapter.CarCalculatorListAdapter
import com.example.carapi.databinding.FragmentCalculatorResultDialogBinding
import com.example.carapi.ui.calculator.CalculatorViewModel

class CalculatorResultDialogFragment : DialogFragment(R.layout.fragment_calculator_result_dialog) {

    private lateinit var binding: FragmentCalculatorResultDialogBinding
    private val viewModel: CalculatorViewModel by activityViewModels()
    private lateinit var carCalculatorAdapter: CarCalculatorListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorResultDialogBinding.inflate(inflater, container, false)

        updateMeasurementData()
        updateCarData()

        loadData()
        setupRv()

        submitData()

        return binding.root
    }


    // override diaglogFragment's onStart method to set transparent dialog background and set margin
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

    override fun onDismiss(dialog: DialogInterface) {
        viewModel.resetData()
        super.onDismiss(dialog)
    }


    private fun setupRv() {
        carCalculatorAdapter = CarCalculatorListAdapter(CarCalculatorClickListener { car ->
            viewModel.onCarClicked(car)

        })
        binding.carsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carCalculatorAdapter
        }
    }


    // load user's cars list and attach it to recyclerView adapter
    private fun loadData() {
        viewModel.getProfileCars()
        viewModel.profileCars.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noCarsTv.visibility = View.VISIBLE
                binding.carsRv.visibility = View.GONE
            } else {
                binding.noCarsTv.visibility = View.GONE
                binding.carsRv.visibility = View.VISIBLE
                viewModel.getProfileCars()
                carCalculatorAdapter.submitList(it)
            }


        }
    }

    // update text data of selected car in textView's
    private fun updateCarData() {
        viewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            if (car != null) {
                binding.apply {
                    makeSummaryTv.text = car.make
                    modelSummaryTv.text = car.model
                    yearSummaryTv.text = car.year
                    typeSummaryTv.text = car.type
                }
            }
        }
    }

    // update text data of measurement in textView's
    private fun updateMeasurementData() {
        viewModel.result.observe(viewLifecycleOwner) {
            val result = getString(R.string.result_tv, it)
            binding.resultTv.text = result
            binding.resultSummaryTv.text = result
        }
    }

    // save measurement in firestore and dismiss the dialog fragment
    private fun submitData() {
        binding.submitBtn.setOnClickListener {
            if (viewModel.selectedCar.value != null && viewModel.result.value != null) {
                viewModel.addMeasurement()
                viewModel.resetData()
                Toast.makeText(requireContext(), "Dodano pomyślnie", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Najpierw wybierz samochód z listy!",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

}