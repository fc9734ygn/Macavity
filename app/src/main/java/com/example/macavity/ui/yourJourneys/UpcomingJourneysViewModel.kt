package com.example.macavity.ui.yourJourneys

import androidx.lifecycle.LiveDataReactiveStreams
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import javax.inject.Inject

class UpcomingJourneysViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository,
    private val journeyRepository: JourneyRepository
) : BaseViewModel() {

    lateinit var currentUserId: String

    private val groupFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher {
            currentUserId = it
            userRepository.fetchUserFlowable(it)
        }
        .flatMap { journeyRepository.fetchUpcomingJourneys(it.groupId!!) }
        .map { allJourneys ->
            val futureJourneys = allJourneys.filter { it.timestamp > System.currentTimeMillis() }
            val currentUserJourneys = futureJourneys.filter { journey ->
                //filtering the ones where current user is not the driver or passenger
                journey.driverId == currentUserId || journey.passengerIds.any { it == currentUserId }
            }
            val sortedJourneys = currentUserJourneys.sortedBy { it.timestamp }
            sortedJourneys
        }

    val upcomingJourneys = LiveDataReactiveStreams.fromPublisher(groupFlowable)
}