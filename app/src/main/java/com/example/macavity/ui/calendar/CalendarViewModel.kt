package com.example.macavity.ui.calendar

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.UpcomingJourney
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import com.example.macavity.utils.daysToMillis
import org.threeten.bp.LocalDate
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository,
    private val journeyRepository: JourneyRepository
) : BaseViewModel() {

    private val userFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { userRepository.fetchUserFlowable(it) }

    private val journeysFlowable = userFlowable
        .flatMap { journeyRepository.fetchUpcomingJourneys(it.groupId!!) }

    val userLiveData = LiveDataReactiveStreams.fromPublisher(userFlowable)
    val allJourneys = LiveDataReactiveStreams.fromPublisher(journeysFlowable)
    val journeysForSelectedDay = MutableLiveData<List<UpcomingJourney>>()

    fun fetchJourneysForSelectedDate(date: LocalDate) {
        val dayStartInMillis = daysToMillis(date.toEpochDay())
        val dayEndInMillis = dayStartInMillis + daysToMillis(1)

        disposable.add(journeysFlowable.subscribe { allJourneys ->
            val journeysInRangeForDay =
                allJourneys.filter { it.timestamp in (dayStartInMillis) until dayEndInMillis }
            val sortedJourneys = journeysInRangeForDay.sortedBy { it.timestamp }
            journeysForSelectedDay.postValue(sortedJourneys)
        })
    }
}
