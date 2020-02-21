package com.example.macavity.data.repositories.user

import com.example.macavity.data.models.Location
import com.example.macavity.data.models.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepository {
    fun getUser(id: String): Flowable<User>
    fun createUser(
        id: String,
        name: String,
        home: Location,
        destination: Location,
        avatarUrl: String,
        email: String,
        phoneNumber: String,
        isDriver: Boolean,
        carNumberPlate: String?,
        carFreeSeats: Int?
    ): Completable
    fun checkIfUserExists(id: String): Maybe<Boolean>
}