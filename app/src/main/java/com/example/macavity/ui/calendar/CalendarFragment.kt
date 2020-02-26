package com.example.macavity.ui.calendar

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User

import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.getRandomBoolean
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_map.toolbar
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
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
        vm = ViewModelProviders.of(this, viewModelFactory).get(CalendarViewModel::class.java)
        initToolbar()
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
        val loc1 = Location(
            "A",
            "Kings Ave 22",
            2.0,
            2.1
        )
        val driver = User(
            "123",
            "John",
            loc1,
            loc1,
            "https://ak8.picdn.net/shutterstock/videos/17741938/thumb/1.jpg",
            "asd",
            "asd",
            true,
            "asd",
            5
            , 2, 2, "A"
        )
        val driver2 = User(
            "123",
            "Eddie",
            loc1,
            loc1,
            "https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "asd",
            "asd",
            true,
            "asd",
            5
            , 2, 2, "A"
        )
        val driver3 = User(
            "123",
            "Eddie",
            loc1,
            loc1,
            "https://media.istockphoto.com/photos/happiness-translates-into-beauty-picture-id610678842?k=6&m=610678842&s=612x612&w=0&h=QA3ZVcH2VzYlrUKjtjMHwBJ6YzQRYDHCztYrjuOPb8w=",
            "asd",
            "asd",
            true,
            "asd",
            5
            , 2, 2, "A"
        )
        journeysAdapter.submitList(
            mutableListOf(
                Journey(
                    "123",
                    driver,
                    2,
                    listOf(driver),
                    1581459108000,
                    "will have to stop by the gas station, should take additional 10mins",
                    loc1, loc1
                ),
                Journey(
                    "123",
                    driver2,
                    2,
                    listOf(driver),
                    1581479108000,
                    "will have to stop by the gas station, should take additional 10mins",
                    loc1, loc1
                ),
                Journey(
                    "123",
                    driver3,
                    4,
                    listOf(driver),
                    1581499108000,
                    "will have to stop by the gas station, should take additional 10mins",
                    loc1, loc1
                )
            ).sortedBy { it.timeStamp }
        )
        journeysAdapter.notifyDataSetChanged()
    }

    @Click(resName = ["fab"])
    fun goToAddJourney() {
        //TODO: hide this fab if user is not a driver

        if (selectedDate == null) {
            toast("Please select a date")
        } else {
            val action =
                CalendarFragment_Directions.actionCalendarFragmentToAddJourneyFragment(selectedDate!!)
            findNavController().navigate(action)
        }
    }
}
