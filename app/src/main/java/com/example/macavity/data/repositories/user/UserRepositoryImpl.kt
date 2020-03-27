package com.example.macavity.data.repositories.user

import com.example.macavity.data.models.firebase.UserFirebase
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.utils.*
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

    override fun fetchUserFlowable(id: String): Flowable<User> {
        return RxFirebaseDatabase.observeValueEvent(
            usersReference.child(id),
            DataSnapshotMapper.of(UserFirebase::class.java)
        ).map { it.toUser() }
    }

    override fun fetchUserSingle(id: String): Single<User> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            usersReference.child(id),
            DataSnapshotMapper.of(UserFirebase::class.java)
        ).map { it.toUser() }.toSingle()
    }

    override fun createUserProfile(
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
            0, 0, ""
        )

        return RxFirebaseDatabase
            .setValue(usersReference.child(id), user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateUserProfile(
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

        val newData: Map<String, Any?> =
            mapOf(
                Pair(FIREBASE_USER_NAME, name),
                Pair(FIREBASE_USER_HOME, home),
                Pair(FIREBASE_USER_DESTINATION, destination),
                Pair(FIREBASE_USER_AVATAR, avatarUrl),
                Pair(FIREBASE_USER_EMAIL, email),
                Pair(FIREBASE_USER_PHONE, phoneNumber),
                Pair(FIREBASE_USER_DRIVER, isDriver),
                Pair(FIREBASE_USER_PLATE, carNumberPlate),
                Pair(FIREBASE_USER_SEATS, carFreeSeats)
            )

        return RxFirebaseDatabase
            .updateChildren(usersReference.child(id), newData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun checkIfUserExists(id: String): Maybe<Boolean> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            usersReference.child(id),
            DataSnapshot::exists
        ).defaultIfEmpty(false)
    }

    override fun fetchUserGroupId(userId: String): Single<String?> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            usersReference.child(userId).child(
                FIREBASE_GROUP_ID
            ), DataSnapshotMapper.of(String::class.java)
        ).toSingle()
    }

    override fun checkIfUserIsInGroup(userId: String): Maybe<Boolean> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            usersReference.child(userId)
                .child(FIREBASE_GROUP_ID),
            DataSnapshot::exists
        ).defaultIfEmpty(false)
    }
}