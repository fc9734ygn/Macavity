package com.example.macavity.data.models.local

data class Journey(
    val id: String,
    val driver: User,
    val freeSeats: Int,
    val passengers: List<User>,
    val timestamp: Long,
    val note: String?,
    val startingLocation: Location,
    val destination: Location
)