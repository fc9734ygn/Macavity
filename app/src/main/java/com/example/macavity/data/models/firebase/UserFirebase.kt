package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
class UserFirebase(
    var id: String = "",
    var name: String = "",
    var home: LocationFirebase = LocationFirebase(),
    var destination: LocationFirebase = LocationFirebase(),
    var avatarUrl: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var isDriver: Boolean = false,
    var carNumberPlate: String? = null,
    var carFreeSeats: Int? = null,
    var passengerStat: Int = 0,
    var driverStat: Int = 0,
    var groupId: String? = null
) {
    fun toUser(): User {
        return User(
            id,
            name,
            home.toLocation(),
            destination.toLocation(),
            avatarUrl,
            email,
            phoneNumber,
            isDriver,
            carNumberPlate,
            carFreeSeats,
            passengerStat,
            driverStat,
            groupId
        )
    }
}