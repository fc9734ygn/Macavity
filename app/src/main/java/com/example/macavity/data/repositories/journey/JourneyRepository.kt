package com.example.macavity.data.repositories.journey

import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.JourneyDetails
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.UpcomingJourney
import io.reactivex.Completable
import io.reactivex.Flowable

interface JourneyRepository {
    fun createJourney(
        groupId: String,
        driverId: String,
        freeSeats: Int,
        timeStamp: Long,
        note: String?,
        startingLocation: Location,
        destination: Location,
        driverAvatarUrl: String
    ): Completable

    fun fetchUpcomingJourneys(groupId: String): Flowable<MutableList<UpcomingJourney>>
    fun fetchJourneyDetails(groupId: String, journeyId: String): Flowable<JourneyDetails>
    fun bookSeat(groupId: String, journeyId: String, userId: String): Completable
    fun cancelJourney(groupId: String, journeyId: String) : Completable
    fun cancelBooking(groupId: String, journeyId: String, userId: String) : Completable
}