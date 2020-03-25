package com.example.macavity.ui.calendar

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.macavity.R
import com.example.macavity.data.models.local.UpcomingJourney
import com.example.macavity.data.models.local.User
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.daysToMillis
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
        JourneysAdapter {
            val action =
                CalendarFragment_Directions.actionCalendarFragmentToJourneyDetailFragment(it.id)
            findNavController().navigate(action)
        }

    private val userObserver = Observer<User> {
        if (it.isDriver) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    private val selectedDayJourneysObserver = Observer<List<UpcomingJourney>> {
        showLoading(false)
        if (it.isEmpty()) {
            //todo: show "no journeys for this date" view
        } else {
            journeysAdapter.submitList(it)
            journeysAdapter.notifyDataSetChanged()
        }
    }

    private val allJourneysObserver = Observer<List<UpcomingJourney>> {
        initCalendar(it)
    }

    @AfterViews
    fun afterViews() {
        initViewModel()
        initToolbar()
        initJourneysRecyclerView()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(CalendarViewModel::class.java)
        vm.userLiveData.observe(this, userObserver)
        vm.journeysForSelectedDay.observe(this, selectedDayJourneysObserver)
        vm.allJourneys.observe(this, allJourneysObserver)
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

    private fun initCalendar(journeys: List<UpcomingJourney>) {
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

                            val dayStartInMillis = daysToMillis(day.date.toEpochDay())
                            val dayEndInMillis = dayStartInMillis + daysToMillis(1)
                            val dayContainsJourneys =
                                journeys.any { it.timestamp in (dayStartInMillis) until dayEndInMillis }
                            journeyIcon.visibility =
                                if (dayContainsJourneys) View.VISIBLE else View.GONE

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
        journeysAdapter.submitList(emptyList())
        showLoading(true)
        vm.fetchJourneysForSelectedDate(date)
    }

    private fun showLoading(show: Boolean){
        loading_indicator.visibility = if (show) View.VISIBLE else View.GONE
        journeys_recycler_view.visibility = if (show) View.GONE else View.VISIBLE
    }

    @Click(resName = ["fab"])
    fun goToAddJourney() {
        if (selectedDate == null) {
            toast(getString(R.string.error_select_date))
        } else {
            val action =
                CalendarFragment_Directions.actionCalendarFragmentToAddJourneyFragment(selectedDate!!)
            findNavController().navigate(action)
        }
    }
}
