package com.example.macavity.data.repositories.user

import com.example.macavity.data.models.Location
import com.example.macavity.data.models.User
import com.example.macavity.utils.FIREBASE_USERS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(databaseReference: DatabaseReference) :
    UserRepository {

    private val usersReference: DatabaseReference = databaseReference.child(FIREBASE_USERS)

    override fun getUser(id: String): Flowable<User> {
        return RxFirebaseDatabase.observeValueEvent(usersReference.child(id), DataSnapshotMapper.of(User::class.java))
    }

    override fun createUser(
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
    ): Completable {

        val user = User(
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
            0, 0, null
        )

        return RxFirebaseDatabase
            .setValue(usersReference.child(id), user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun checkIfUserExists(id: String): Maybe<Boolean> {
        return RxFirebaseDatabase.observeSingleValueEvent(usersReference.child(id), DataSnapshot:: exists).defaultIfEmpty(false)
    }
}