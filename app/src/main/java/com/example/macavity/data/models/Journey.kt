package com.example.macavity.data.models

data class Journey(
    val id: String,
    val driverId: String,
    val driverName: String,
    val freeSeats: Int,
    val takenSeats: Int,
    val passengers: List<String>,
    val timeStamp: Long
)