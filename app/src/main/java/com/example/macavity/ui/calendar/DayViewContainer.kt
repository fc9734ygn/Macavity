package com.example.macavity.ui.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.view_calendar_day.view.*
import org.threeten.bp.LocalDate

class DayViewContainer(view: View, private val clickListener: (LocalDate) -> Unit) : ViewContainer(view) {
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val dayText = view.day_text
    val journeyIcon = view.journey_icon
    val backgroundView = view.background_view

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                clickListener.invoke(day.date)
            }
        }
    }
}