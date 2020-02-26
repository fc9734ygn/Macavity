package com.example.macavity.data.models.local

import com.example.macavity.data.models.firebase.LocationFirebase

data class Location(
    val placeId: String,
    val address: String,
    val longitude: Double,
    val latitude: Double
)