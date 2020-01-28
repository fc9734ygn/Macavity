package com.example.macavity.ui.calendar

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.Journey
import com.example.macavity.data.models.User

import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.getRandomBoolean
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_map.toolbar
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.*

@EFragment(resName = "fragment_calendar")
open class CalendarFragment : HomeFragment() {

    private lateinit var vm: CalendarViewModel
    private var selectedDate: LocalDate? = null
    private val journeysAdapter =
        JourneysAdapter { findNavController().navigate(R.id.action_calendarFragment__to_journeyDetailFragment_) }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
        initToolbar()
        toggleBottomNav(true)
        initJourneysRecyclerView()
        initCalendar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
        toolbar.startIconListener = { openDrawer() }
    }

    private fun initJourneysRecyclerView() {
        journeys_recycler_view.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        journeys_recycler_view.adapter = journeysAdapter
    }

    private fun initCalendar() {
        val currentMonth: YearMonth = YearMonth.now()
        val firstMonth: YearMonth = currentMonth
        val lastMonth: YearMonth = currentMonth.plusMonths(2)
        val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val today = LocalDate.now()
        val dayClickListener: (LocalDate) -> Unit = { selectDate(it) }

        calendar.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendar.scrollToMonth(currentMonth)
        selectDate(today)

        calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view, dayClickListener)
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
                                    R.color.white,
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
            val titleFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
            toolbar.setTitle(titleFormatter.format(it.yearMonth))
            val dayToHighlight = if (it.yearMonth != currentMonth) it.yearMonth.atDay(1) else today
            selectDate(dayToHighlight)
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate == date) return
        val oldDate = selectedDate
        selectedDate = date
        oldDate?.let { calendar.notifyDateChanged(it) }
        calendar.notifyDateChanged(date)
        updateAdapterForDate(date)
    }

    private fun updateAdapterForDate(date: LocalDate) {
        //todo: use real data
        val driver = User(
            "123",
            "John",
            "Kings Ave 22",
            "University of Kent",
            Pair(123.00, 123.00),
            Pair(23.2, 12.2),
            "https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc=",
            "asd",
            "asd",
            true,
            "asd",
            5
            , 2, 2
        )
        journeysAdapter.submitList(
            mutableListOf(
                Journey("123", driver, 2, 1, listOf("asd", "gt"), 42124432),
                Journey("123", driver, 2, 1, listOf("asd", "gt"), 42124432),
                Journey("123", driver, 2, 1, listOf("asd", "gt"), 42124432),
                Journey("123", driver, 2, 1, listOf("asd", "gt"), 42124432),
                Journey("123", driver, 2, 1, listOf("asd", "gt"), 42124432),
                Journey("123", driver, 4, 2, listOf("afa", "cgw"), 42142443)
            )
        )

//        journeysAdapter.currentList.addAll()
        journeysAdapter.notifyDataSetChanged()
    }
}
