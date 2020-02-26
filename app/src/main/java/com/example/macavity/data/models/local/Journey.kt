package com.example.macavity.data.models

data class Journey(
    val id: String,
    val driver: User,
    val freeSeats: Int,
    val passengers: List<User>,
    val timeStamp: Long,
    val note: String?,
    val startingLocation: Location,
    val destination: Location
)