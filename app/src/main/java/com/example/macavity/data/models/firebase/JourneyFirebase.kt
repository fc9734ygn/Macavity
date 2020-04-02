package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.JourneyDetails
import com.example.macavity.data.models.local.UpcomingJourney
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class JourneyFirebase(
    val id: String = "",
    val driverId: String = "",
    val freeSeats: Int = 0,
    val passengerIds: Map<String, Boolean>? = emptyMap(),
    val timestamp: Long = 0,
    val note: String? = null,
    val startingLocation: LocationFirebase = LocationFirebase(),
    val destination: LocationFirebase = LocationFirebase(),
    val driverAvatarUrl: String = ""
) {
    fun toUpcomingJourney(): UpcomingJourney {
        return UpcomingJourney(id, driverId, timestamp, driverAvatarUrl, freeSeats, passengerIds?.keys?.toList())
    }

    fun toJourneyDetails(): JourneyDetails {
        return JourneyDetails(
            id,
            freeSeats,
            driverId,
            passengerIds?.keys?.toList(),
            note,
            timestamp,
            startingLocation.toLocation(),
            destination.toLocation()
        )
    }
}