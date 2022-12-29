package com.example.carapi.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carapi.R
import com.example.carapi.adapter.CalendarAdapter.CalendarViewHolder
import com.example.carapi.databinding.ItemCalendarCellBinding
import java.time.LocalDate

class CalendarAdapter(
    private val clickListener: CalendarClickListener,
    private val selectedDate: LocalDate,
    private val dbArray: ArrayList<String>
) : ListAdapter<LocalDate, CalendarViewHolder>(DiffCallback) {

    inner class CalendarViewHolder(private var binding: ItemCalendarCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: CalendarClickListener,
            date: LocalDate
        ) {


            binding.calendarClickListener = clickListener
            binding.currentDate = date


            if (date == LocalDate.of(1930, 9, 30)) {
                binding.dayTv.apply {
                    text = ""
                    background = null
                    foreground = null
                }
            } else {
                binding.dayTv.text = date.dayOfMonth.toString()

                if (date == selectedDate) {
                    binding.calendarCallLayout.setBackgroundColor(Color.DKGRAY)
                    var eventTriggerFlag = false
                    for (dates in dbArray) {
                        if (dates == date.toString()) {
                            eventTriggerFlag = true
                        }
                    }
                    if (eventTriggerFlag) {
                        binding.dayTv.setBackgroundResource(R.drawable.calendar_text_background_red)
                    } else {
                        binding.dayTv.setBackgroundResource(R.drawable.calendar_tv_background)
                    }
                } else {
                    var eventTriggerFlag = false
                    for (dates in dbArray) {
                        if (dates == date.toString()) {
                            eventTriggerFlag = true
                        }
                    }
                    if (eventTriggerFlag) {
                        binding.dayTv.setBackgroundResource(R.drawable.calendar_text_background_red)
                    } else {
                        binding.dayTv.setBackgroundResource(R.drawable.calendar_tv_background)
                    }

                }

            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = ItemCalendarCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = getItem(position)
        holder.bind(clickListener, date)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return oldItem == newItem
        }

    }


}

class CalendarClickListener(val clickListener: (date: LocalDate) -> Unit) {
    fun onClick(date: LocalDate) = clickListener(date)
}