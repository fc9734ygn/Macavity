package com.example.macavity.data.repositories.journey

import com.example.macavity.data.models.local.Location
import io.reactivex.Completable

interface JourneyRepository {
    fun createJourney(
        groupId: String,
        driverId: String,
        freeSeats: Int,
        timeStamp: Long,
        note: String?,
        startingLocation: Location,
        destination: Location
    ): Completable
}