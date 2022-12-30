package com.example.carapi.repository.calendar

import java.time.LocalDate

interface CalendarRepository {

    fun addDates(date: LocalDate)

    suspend fun getDates() : List<String>

    fun deleteDates(date : LocalDate)
}