package com.example.macavity.data.models

data class User(
    val id: String,
    val name: String,
    val locationAddress: String,
    val destinationAddress: String,
    val locationCoordinate: Pair<Double, Double>,
    val destinationCoordinate : Pair<Double, Double>,
    val avatarUrl: String,
    val email: String,
    val phoneNumber: String,
    val isDriver: Boolean,
    val carNumberPlate: String,
    val carFreeSeats: Int,
    val passengerStat: Int,
    val driverStat: Int
)