package com.example.macavity.data.models.local

data class JourneyDetails(val id: String,
                          val freeSeats: Int,
                          val driverId: String,
                          val passengerIds: List<String>?, //fixme: maybe make default value empty list instead of nullable
                          val driversNote: String?,
                          val timestamp: Long,
                          val startingLocation: Location,
                          val destination: Location
                          )