package com.example.carapi.ui.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carapi.repository.calendar.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(private val calendarRepository: CalendarRepository) :
    ViewModel() {

    val selectedDate = MutableLiveData<LocalDate>()

    val currentMonthYear = MutableLiveData<String>()

    val days = MutableLiveData<ArrayList<LocalDate>>(ArrayList())

    val busyDates = MutableLiveData<List<String>>(emptyList())

    init {
        getDatesFromDatabase()
        selectedDate.value = LocalDate.now()
        days.value = daysInMonthArray(selectedDate.value!!)
    }

    fun addDateToDatabase(date: LocalDate) {
        calendarRepository.addDates(date)
    }

    fun getDatesFromDatabase() {
        viewModelScope.launch {
            busyDates.value = calendarRepository.getDates()
        }
    }

    fun deleteDate(date: LocalDate){
        calendarRepository.deleteDates(date)
    }

    fun onCalendarItemClicked(date: LocalDate) {
        selectedDate.value = date
        days.value = daysInMonthArray(selectedDate.value!!)
    }

    fun monthYearFromDate(date: LocalDate) {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        currentMonthYear.value = date.format(formatter)
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<LocalDate> {
        val daysInMonthArrayVar = ArrayList<LocalDate>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.value?.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth?.dayOfWeek?.value
        for (i in 1..42) {
            if (i <= dayOfWeek!! || i > (daysInMonth + dayOfWeek)) {
                daysInMonthArrayVar.add(LocalDate.of(1930, 9, 30))
            } else {
                daysInMonthArrayVar.add(
                    LocalDate.of(
                        selectedDate.value?.year!!,
                        selectedDate.value?.month,
                        i - dayOfWeek
                    )
                )
            }
        }
        return daysInMonthArrayVar
    }

    fun updateDays() {
        days.value = daysInMonthArray(selectedDate.value!!)
    }

    fun nextMonth() {
        selectedDate.value = selectedDate.value?.plusMonths(1)
        monthYearFromDate(selectedDate.value!!)
        days.value = daysInMonthArray(selectedDate.value!!)
    }

    fun previousMonth() {
        selectedDate.value = selectedDate.value?.minusMonths(1)
        monthYearFromDate(selectedDate.value!!)
        days.value = daysInMonthArray(selectedDate.value!!)
    }

}