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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        hideBottomNav()

        addButtonHandle()
        saveButtonHandle()

        handleMonthChange()
        handleBusyDatesFromDatabase()
        handleSelectedDateUpdateRv()



        return binding.root
    }

    // load days in month data by observing viewModel days value

    private fun handleBusyDatesFromDatabase() {

        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            Log.d("selected date", date.toString())
            viewModel.monthYearFromDate(date)
            binding.monthYearTv.text = viewModel.currentMonthYear.value
            viewModel.busyDates.observe(viewLifecycleOwner) {
                setupRv(date, it)
            }
            setupRv(date, viewModel.busyDates.value!!)
            viewModel.getDatesFromDatabase()
        }


    }


    // update recycler view with focus on selected date  by observing viewModel selected date value
    private fun handleSelectedDateUpdateRv() {


    }

    // initial recycler view setup
    private fun setupRv(date: LocalDate, busyDates: List<String>) {
        calendarAdapter = CalendarAdapter(CalendarClickListener { clickedDate ->
            if (clickedDate != LocalDate.of(1930, 9, 30)) {
                viewModel.onCalendarItemClicked(clickedDate)
                if (isAddButtonClicked) {
                    viewModel.addDateToDatabase(clickedDate)
                }
            }
            setupRv(clickedDate, busyDates)
        }, date, busyDates)
        binding.calendarRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = calendarAdapter
        }
        viewModel.updateDays()
        calendarAdapter.submitList(viewModel.days.value)


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

    private fun saveButtonHandle() {
        binding.saveDateBtn.setOnClickListener {
            binding.addDateBtn.visibility = View.VISIBLE
            binding.deleteDateBtn.visibility = View.VISIBLE
            binding.saveDateBtn.visibility = View.GONE
            isAddButtonClicked = false
        }
    }
}