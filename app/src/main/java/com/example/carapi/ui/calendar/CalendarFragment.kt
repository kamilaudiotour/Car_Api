package com.example.carapi.ui.calendar

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var selectedDate: LocalDate
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var dbArray: ArrayList<String>
    private val viewModel: CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        hideBottomNav()

        dbArray = ArrayList()

        handleMonthChange()
        handleSelectedDateUpdateRv()
        loadData()



        return binding.root
    }
    // load days in month data by observing viewmodel days value
    private fun loadData(){
        viewModel.days.observe(viewLifecycleOwner) { days ->
            calendarAdapter.submitList(days)

            Log.d("days fragment", days.toString())
        }
    }


    // update recycler view with focus on selected date  by observing viewModel selected date value
    private fun handleSelectedDateUpdateRv(){
        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            selectedDate = date
            viewModel.monthYearFromDate(selectedDate)
            binding.monthYearTv.text = viewModel.currentMonthYear.value
            setupRv()


        }
    }

    // initial recycler view setup
    private fun setupRv() {
        calendarAdapter = CalendarAdapter(CalendarClickListener { date ->
            if (date != LocalDate.of(1930, 9, 30)) {
                viewModel.onCalendarItemClicked(date)
                Log.d("clicked date", date.toString())
            }

        }, selectedDate, dbArray)
        binding.calendarRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = calendarAdapter
        }


    }

    // listen buttons click to change month and update data
   private fun handleMonthChange(){
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
}