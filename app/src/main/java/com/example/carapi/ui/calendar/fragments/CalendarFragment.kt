package com.example.carapi.ui.calendar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.carapi.R
import com.example.carapi.adapter.CalendarAdapter
import com.example.carapi.adapter.CalendarClickListener
import com.example.carapi.databinding.FragmentCalendarBinding
import com.example.carapi.ui.calendar.CalendarViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var calendarAdapter: CalendarAdapter
    private val viewModel: CalendarViewModel by activityViewModels()
    private var isAddButtonClicked = false
    private var isDeleteButtonClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        hideBottomNav()

        addButtonHandle()
        deleteButtonHandle()
        saveButtonHandle()

        handleMonthChange()
        handleSelectedDateChange()

        return binding.root
    }


    // load days in month data by observing viewModel days value
    private fun loadCurrentMonthDaysData(){
        viewModel.updateDays()
        calendarAdapter.submitList(viewModel.days.value)
    }


    // update recycler view with latest list of busy dates list from firestore db
    private fun handleBusyDatesFromDatabase(date: LocalDate) {
        viewModel.busyDates.observe(viewLifecycleOwner) {
            setupRv(date, it)
        }
    }

    // delete given date from firestore database
    private fun deleteDate(date: LocalDate){
        viewModel.deleteDate(date)
    }


    // update recycler view with focus on selected date  by observing viewModel selected date value
    private fun handleSelectedDateChange() {
        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            handleBusyDatesFromDatabase(date)
            viewModel.monthYearFromDate(date)
            binding.monthYearTv.text = viewModel.currentMonthYear.value
            setupRv(date, viewModel.busyDates.value!!)
            viewModel.getDatesFromDatabase()
        }
    }


    // initial recycler view setup and item click handling
    private fun setupRv(date: LocalDate, busyDates: List<String>) {
        calendarAdapter = CalendarAdapter(CalendarClickListener { clickedDate ->
            if (clickedDate != LocalDate.of(1930, 9, 30)) {
                viewModel.onCalendarItemClicked(clickedDate)
                if (isAddButtonClicked) {
                    Log.d("clicked date", clickedDate.toString())
                    viewModel.addDateToDatabase(clickedDate)
                }
                if(isDeleteButtonClicked){
                    deleteDate(clickedDate)
                }
            }
        }, date, busyDates)
        binding.calendarRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = calendarAdapter
        }
        loadCurrentMonthDaysData()
    }

    // listen buttons click to change month and update data
    private fun handleMonthChange() {
        binding.previousBtn.setOnClickListener {
            viewModel.previousMonth()
        }
        binding.nextBtn.setOnClickListener {
            viewModel.nextMonth()
        }
    }

    private fun hideBottomNav() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.visibility = View.GONE
    }

    private fun addButtonHandle() {
        binding.addDateBtn.setOnClickListener {
            binding.addDateBtn.visibility = View.GONE
            binding.deleteDateBtn.visibility = View.GONE
            binding.saveDateBtn.visibility = View.VISIBLE
            isAddButtonClicked = true
        }
    }
    private fun deleteButtonHandle() {
        binding.deleteDateBtn.setOnClickListener {
            binding.addDateBtn.visibility = View.GONE
            binding.deleteDateBtn.visibility = View.GONE
            binding.saveDateBtn.visibility = View.VISIBLE
            isDeleteButtonClicked = true
        }
    }

    private fun saveButtonHandle() {
        binding.saveDateBtn.setOnClickListener {
            binding.addDateBtn.visibility = View.VISIBLE
            binding.deleteDateBtn.visibility = View.VISIBLE
            binding.saveDateBtn.visibility = View.GONE
            isAddButtonClicked = false
            isDeleteButtonClicked = false
        }
    }
}