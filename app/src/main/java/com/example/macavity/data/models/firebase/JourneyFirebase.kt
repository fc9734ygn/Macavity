package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Journey
import com.example.macavity.data.models.local.UpcomingJourney
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class JourneyFirebase(
    val id: String = "",
    val driverId: String = "",
    val freeSeats: Int = 0,
    val passengerIds: List<String> = emptyList(),
    val timestamp: Long = 0,
    val note: String? = null,
    val startingLocation: LocationFirebase = LocationFirebase(),
    val destination: LocationFirebase = LocationFirebase(),
    val driverAvatarUrl: String = ""
) {
    fun toUpcomingJourney(): UpcomingJourney {
        return UpcomingJourney(id, driverId, timestamp, driverAvatarUrl, freeSeats, passengerIds)
    }
}