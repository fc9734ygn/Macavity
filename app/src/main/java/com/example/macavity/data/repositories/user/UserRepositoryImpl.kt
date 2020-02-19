package com.example.macavity.data.repositories.user

import com.example.macavity.data.models.Location
import com.example.macavity.data.models.User
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(databaseReference: DatabaseReference) :
    UserRepository {

    private val usersReference: DatabaseReference = databaseReference.child("users")

    override fun getUser(id: String): Flowable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(
        name: String,
        home: Location,
        destination: Location,
        avatarUrl: String,
        email: String,
        phoneNumber: String,
        isDriver: Boolean,
        carNumberPlate: String,
        carFreeSeats: Int
    ): Completable {
        val key: String? = usersReference.push().key
        return RxFirebaseDatabase.setValue(
            usersReference.child(key!!),
            User(
                key,
                name,
                home,
                destination,
                avatarUrl,
                email,
                phoneNumber,
                isDriver,
                carNumberPlate,
                carFreeSeats,
                0, 0
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}