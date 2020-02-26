package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Location
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
class LocationFirebase(
    var placeId: String = "",
    var address: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0
) {
    fun toLocation(): Location {
        return Location(placeId, address, longitude, latitude)
    }
}