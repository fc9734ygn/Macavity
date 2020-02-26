package com.example.macavity.data.models

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
class FirebaseUser(
    var id: String = "",
    var name: String = "",
    var home: Location = Location("", "", 0.0, 0.0),
    var destination: Location = Location("", "", 0.0, 0.0),
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
            home,
            destination,
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