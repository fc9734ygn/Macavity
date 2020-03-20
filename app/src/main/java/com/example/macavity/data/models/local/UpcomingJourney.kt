package com.example.macavity.data.models.local

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class UpcomingJourney(
    val id: String,
    val driverId: String,
    val timestamp: Long,
    val driverAvatarUrl: String,
    val freeSeats: Int,
    val passengerIds: List<String>?
)