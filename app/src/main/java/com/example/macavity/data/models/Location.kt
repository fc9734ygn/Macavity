package com.example.macavity.data.models

data class Location(
    val placeId: String,
    val address: String,
    val coordinate: Pair<Double, Double>
)