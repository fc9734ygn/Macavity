package com.example.macavity.data.models

data class JourneyFirebase(
    val id: String,
    val driverId: String,
    val freeSeats: Int,
    val passengerIds: List<String>,
    val timeStamp: Long,
    val note: String?,
    val startingLocation: Location,
    val destination: Location
)