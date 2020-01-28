package com.example.macavity.ui.calendar

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R

import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.getRandomBoolean
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.toolbar
import kotlinx.android.synthetic.main.view_calendar_day.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.text.SimpleDateFormat
import java.util.*

@EFragment(resName = "fragment_calendar")
open class CalendarFragment : HomeFragment() {

    private lateinit var vm: CalendarViewModel
    private var selectedDate: LocalDate? = null
    private val currentMonth: YearMonth = YearMonth.now()
    private val firstMonth: YearMonth = currentMonth
    private val lastMonth: YearMonth = currentMonth.plusMonths(2)
    private val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    private val today = LocalDate.now()
    private val journeysAdapter = JourneysAdapter { vm.bookSeats(it.id) }
    private val clickListener: (LocalDate) -> Unit = { selectDate(it) }
    private val titleFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")


    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        initToolbar()
        toggleBottomNav(true)
        initCalendar()
        initJourneysRecyclerView()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_calendar))

        toolbar.startIconListener = { openDrawer() }
    }

    private fun initJourneysRecyclerView(){
        journeys_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        journeys_recycler_view.adapter = journeysAdapter
    }

    private fun initCalendar() {
        calendar.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendar.scrollToMonth(currentMonth)
        selectDate(today)

        calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view, clickListener)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val dayText = container.dayText
                val journeyIcon = container.journeyIcon
                val backgroundView = container.backgroundView
                dayText.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    dayText.visibility = View.VISIBLE
                    journeyIcon.visibility = View.GONE
                    when (day.date) {
                        today -> {
                            dayText.setTextColor(dayText.context.getColor(R.color.white))
                            backgroundView.setBackgroundResource(R.drawable.background_day_today)
                        }
                        selectedDate -> {
                            dayText.setTextColor(dayText.context.getColor(R.color.black))
                            backgroundView.setBackgroundResource(R.drawable.background_day_selected)
                        }
                        else -> {
                            dayText.setTextColor(dayText.context.getColor(R.color.black))
                            backgroundView.setBackgroundColor(
                                resources.getColor(
                                    R.color.grey_light,
                                    null
                                )
                            )
                            //todo: change according if there are any journeys happening that day
                            journeyIcon.visibility =
                                if (getRandomBoolean()) View.VISIBLE else View.GONE

                        }
                    }
                } else {
                    //dates outside of current month
                    dayText.setTextColor(dayText.context.getColor(R.color.grey_dark))
                    journeyIcon.visibility = View.GONE
                }
            }
        }

        calendar.monthScrollListener = {
          //  month_title.text = titleFormatter.format(it.yearMonth)
            toolbar.setTitle(titleFormatter.format(it.yearMonth))
            val dayToHighlight = if (it.yearMonth != currentMonth) it.yearMonth.atDay(1) else today
            selectDate(dayToHighlight)
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { calendar.notifyDateChanged(it) }
            calendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        //todo: write logic for recyclerview
        journeysAdapter.currentList.clear()
//        journeysAdapter.currentList.addAll()
        journeysAdapter.notifyDataSetChanged()
    }
}
