package com.example.carapi.ui.order.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.CarCalculatorClickListener
import com.example.carapi.adapter.CarCalculatorListAdapter
import com.example.carapi.databinding.FragmentCreateOrderBinding
import com.example.carapi.models.Car
import com.example.carapi.ui.order.CreateOrderViewModel
import com.example.carapi.util.nameValidation
import com.example.carapi.util.phoneValidation
import com.example.carapi.util.radioGroupValidation

class CreateOrderFragment : Fragment(R.layout.fragment_create_order) {

    private lateinit var binding: FragmentCreateOrderBinding
    private lateinit var carOrderAdapter: CarCalculatorListAdapter
    private val viewModel: CreateOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateOrderBinding.inflate(inflater, container, false)

        validate()
        submitData()

        setupRv()
        loadData()

        adminFabHandle()
        handleFabClick()

        return binding.root
    }

    private fun setupRv() {
        carOrderAdapter = CarCalculatorListAdapter(CarCalculatorClickListener { car ->
            viewModel.onCarClicked(car)
            binding.carTv.visibility = View.VISIBLE
            binding.carTv.text = binding.root.context.getString(R.string.make_model_summary_tv, car.make, car.model)
        })
        binding.carsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = carOrderAdapter
        }
    }

    private fun loadData() {
        viewModel.getProfileCars()
        viewModel.profileCars.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.noCarsTv.visibility = View.GONE
                binding.carsRv.visibility = View.VISIBLE
                binding.chosenCarTv.visibility = View.VISIBLE
                carOrderAdapter.submitList(it)
            } else {
                binding.noCarsTv.visibility = View.VISIBLE
                binding.carsRv.visibility = View.GONE
                binding.chosenCarTv.visibility = View.GONE

            }
        }
    }

    private fun validate() {
        nameValidation(binding.nameSurnameEt)
        phoneValidation(binding.phoneNumberEt)
        radioGroupValidation(
            binding.foilRg,
            binding.foilVariantsTv,
            binding.readyColorRb.id,
            binding.remarksEt
        )
    }

    private fun submitData() {
        binding.apply {
            submitBtn.setOnClickListener {
                if (nameValidation(binding.nameSurnameEt) && phoneValidation(binding.phoneNumberEt) && radioGroupValidation(
                        binding.foilRg,
                        binding.foilVariantsTv,
                        binding.readyColorRb.id,
                        binding.remarksEt
                    )
                ) {
                    val checkedRbId = binding.foilRg.checkedRadioButtonId
                    val checkedRb: RadioButton = binding.foilRg.findViewById(checkedRbId)
                    if (viewModel.selectedCar.value != null) {
                        val nameSurname = nameSurnameEt.text.toString()
                        val phoneNumber = phoneNumberEt.text.toString()
                        val foilVariant = checkedRb.text.toString()
                        val email = viewModel.currentUser?.email.toString()
                        val car = viewModel.selectedCar.value!!
                        val remarks = remarksEt.text.toString()
                        sendEmail(
                            nameSurname,
                            phoneNumber,
                            foilVariant,
                            email,
                            car,
                            remarks
                        )
                        viewModel.addOrder(nameSurname, phoneNumber, remarks, foilVariant)
                    } else {
                        Toast.makeText(requireContext(), "Wybierz auto z listy", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    if (nameSurnameEt.text.isEmpty()) nameSurnameEt.error = "Nie wprowadzono danych"
                    if (phoneNumberEt.text.isEmpty()) phoneNumberEt.error = "Nie wprowadzono danych"
                    if (foilRg.checkedRadioButtonId == -1) foilVariantsTv.error =
                        "Nie zaznaczono opcji"
                    if (foilRg.checkedRadioButtonId == readyColorRb.id) remarksEt.error =
                        "Nie wpisano numeru koloru"
                }
            }
        }
    }

    private fun sendEmail(
        name: String,
        phoneNumber: String,
        foilVariant: String,
        email: String,
        car: Car,
        remarks: String
    ) {
        val receiver = "foilapp@gmail.com"
        val receiversList = arrayOf(receiver)
        val topic = "Zamówienie"
        val content =
            "Imię i nazwisko: " + name + "\r\n" + " Email: " + email + "\r\n" + "Numer telefonu: " + phoneNumber + "\r\n" + "Wariant folii: " + foilVariant + "\r\n" + "Uwagi: " + remarks + "\r\n" + "Marka auta: " + car.make + "\r\n" + "Model auta: " + car.model + "\r\n" + "Typ nadwozia: " + car.type + "\r\n" + "Rocznik: " + car.year
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, receiversList)
            putExtra(Intent.EXTRA_SUBJECT, topic)
            putExtra(Intent.EXTRA_TEXT, content)
            startActivity(Intent.createChooser(intent, "Email"))
        }

    }

    private fun adminFabHandle() {
        viewModel.isUserAdmin()
        viewModel.isUserAdmin.observe(viewLifecycleOwner) { isAdmin ->
            if (!isAdmin) {
                binding.orderListFab.visibility = View.GONE
            } else {
                binding.orderListFab.visibility = View.VISIBLE
            }
            viewModel.isUserAdmin()
        }
    }

    private fun handleFabClick() {
        binding.orderListFab.setOnClickListener {
            findNavController().navigate(R.id.action_createOrderFragment_to_ordersListDialogFragment)
        }
    }

}