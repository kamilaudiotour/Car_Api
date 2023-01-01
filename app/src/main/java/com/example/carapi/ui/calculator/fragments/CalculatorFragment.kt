package com.example.carapi.ui.calculator.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carapi.R
import com.example.carapi.databinding.FragmentCalculatorBinding
import com.example.carapi.ui.calculator.CalculatorViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private lateinit var binding: FragmentCalculatorBinding
    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)


        saveInputState()
        hideBottomNav()
        launchWebView()
        validation()

        return binding.root
    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }


    private fun validation() {
        binding.apply {
            confirmBtn.setOnClickListener {

                val sideText = sideEt.text.toString()
                val backText = backEt.text.toString()
                val bumperText = bumperEt.text.toString()
                val bonnetWidthText = bonnetWidthEt.text.toString()
                val bonnetHeightText = bonnetHeightEt.text.toString()
                val roofWidthText = roofWidthEt.text.toString()
                val roofHeightText = roofHeightEt.text.toString()

                if (sideText.isBlank()) {
                    sideEt.error = "Puste pole"
                } else {
                    if (sideText.toInt() < 100 || sideText.toInt() > 1000) {
                        sideEt.error = "Niepoprawne dane"
                    } else sideEt.error = null
                }
                if (backText.isBlank()) {
                    backEt.error = "Puste pole"
                } else {
                    if (backText.toInt() < 20 || backText.toInt() > 500) {
                        backEt.error = "Niepoprawne dane"
                    } else backEt.error = null
                }
                if (bumperText.isBlank()) {
                    bumperEt.error = "Puste pole"
                } else {
                    if (bumperText.toInt() < 20 || bumperText.toInt() > 1000) {
                        bumperEt.error = "Niepoprawne dane"
                    } else bumperEt.error = null
                }
                if (bonnetWidthText.isBlank()) {
                    bonnetWidthEt.error = "Puste pole"
                } else {
                    if (bonnetWidthText.toInt() < 20 || bonnetWidthText.toInt() > 1000) {
                        bonnetWidthEt.error = "Niepoprawne dane"
                    } else bonnetWidthEt.error = null
                }

                if (bonnetHeightText.isBlank()) {
                    bonnetHeightEt.error = "Puste pole"
                } else {
                    if (bonnetHeightText.toInt() < 20 || bonnetHeightText.toInt() > 1000) {
                        bonnetHeightEt.error = "Niepoprawne dane"
                    } else bonnetHeightEt.error = null
                }
                if (roofWidthText.isBlank()) {
                    roofWidthEt.error = "Puste pole"
                } else {
                    if (roofWidthText.toInt() < 20 || roofWidthText.toInt() > 1000) {
                        roofWidthEt.error = "Niepoprawne dane"
                    } else roofWidthEt.error = null
                }
                if (roofHeightText.isBlank()) {
                    roofHeightEt.error = "Puste pole"
                } else {
                    if (roofHeightText.toInt() < 20 || roofHeightText.toInt() > 1000) {
                        roofHeightEt.error = "Niepoprawne dane"
                    } else roofHeightEt.error = null
                }

                val errorList = listOf(
                    sideEt.error,
                    backEt.error,
                    bumperEt.error,
                    bonnetWidthEt.error,
                    bonnetHeightEt.error,
                    roofWidthEt.error,
                    roofHeightEt.error
                ).any { it != null }

                if (errorList) {
                    Toast.makeText(requireContext(), "Nieprawidłowe dane", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.readValidatedForm(
                        sideText,
                        backText,
                        bumperText,
                        bonnetWidthText,
                        bonnetHeightText,
                        roofWidthText,
                        roofHeightText
                    )
                    viewModel.calculateResult()

                    findNavController().navigate(R.id.action_calculatorFragment_to_calculatorResultDialogFragment)
                }

            }

        }
    }

    private fun saveInputState() {
        binding.apply {
            if (viewModel.sideLengthData.value != 0.0) {
                sideEt.setText(viewModel.sideLengthData.value?.toInt().toString())
            }

            if (viewModel.sideLengthData.value != 0.0) {
                bumperEt.setText(viewModel.bumperLengthData.value?.toInt().toString())
            }
            if (viewModel.backLengthData.value != 0.0) {
                backEt.setText(viewModel.backLengthData.value?.toInt().toString())
            }
            if (viewModel.bonnetWidthLengthData.value != 0.0) {
                bonnetWidthEt.setText(viewModel.bonnetWidthLengthData.value?.toInt().toString())
            }
            if (viewModel.bonnetHeightLengthData.value != 0.0) {
                bonnetHeightEt.setText(viewModel.bonnetHeightLengthData.value?.toInt().toString())
            }
            if (viewModel.roofWidthLengthData.value != 0.0) {
                roofWidthEt.setText(viewModel.roofWidthLengthData.value?.toInt().toString())
            }
            if (viewModel.roofHeightLengthData.value != 0.0) {
                roofHeightEt.setText(viewModel.roofHeightLengthData.value?.toInt().toString())
            }


        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun launchWebView() {
        val webVideo = binding.ytVideoWv
        webVideo.settings.javaScriptEnabled = true
        webVideo.webChromeClient = object : WebChromeClient() {}
        webVideo.loadData(
            "<html><body style=\"padding: 0; margin: 0;\"><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/gdSRg-BRxgc\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" </iframe></body></html>",
            "text/html",
            "utf-8"
        )
    }

}