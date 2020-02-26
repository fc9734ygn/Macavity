package com.example.macavity.data.repositories.user

import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface UserRepository {
    fun getUserFlowable(id: String): Flowable<User>
    fun getUserMaybe(id: String): Single<User>
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