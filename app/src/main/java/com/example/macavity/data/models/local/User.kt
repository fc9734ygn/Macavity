package com.example.macavity.data.models.local

data class User(
    val id: String,
    val name: String,
    val home: Location,
    val destination: Location,
    val avatarUrl: String,
    val email: String,
    val phoneNumber: String,
    val driver: Boolean,
    val carNumberPlate: String?,
    val carFreeSeats: Int?,
    val passengerStat: Int,
    val driverStat: Int,
    val groupId: String?
)