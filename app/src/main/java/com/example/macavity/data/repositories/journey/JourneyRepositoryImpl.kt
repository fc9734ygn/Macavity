package com.example.macavity.data.repositories.journey

import com.example.macavity.data.models.firebase.JourneyFirebase
import com.example.macavity.data.models.firebase.LocationFirebase
import com.example.macavity.data.models.local.JourneyDetails
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.UpcomingJourney
import com.example.macavity.utils.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class JourneyRepositoryImpl @Inject constructor(private val databaseReference: DatabaseReference) :
    JourneyRepository {

    override fun createJourney(
        groupId: String,
        driverId: String,
        freeSeats: Int,
        timeStamp: Long,
        note: String?,
        startingLocation: Location,
        destination: Location,
        driverAvatarUrl: String,
        driverStat: Int
    ): Completable {

        val startingLocationFirebase = LocationFirebase(
            startingLocation.placeId,
            startingLocation.address,
            startingLocation.latitude,
            startingLocation.longitude
        )

        val destinationFirebase = LocationFirebase(
            destination.placeId,
            destination.address,
            destination.latitude,
            destination.longitude
        )

        return doesJourneysNodeExist(groupId)
            .flatMapCompletable { exists ->
                if (exists) {
                    addJourney(
                        driverId,
                        freeSeats,
                        timeStamp,
                        note,
                        startingLocationFirebase,
                        destinationFirebase,
                        groupId,
                        driverAvatarUrl
                    ).andThen(incrementDriverStat(driverId, driverStat))
                } else {
                    createJourneysNode(groupId)
                        .andThen(
                            addJourney(
                                driverId,
                                freeSeats,
                                timeStamp,
                                note,
                                startingLocationFirebase,
                                destinationFirebase,
                                groupId,
                                driverAvatarUrl
                            )
                        ).andThen(incrementDriverStat(driverId, driverStat))
                }
            }
    }

    private fun incrementDriverStat(driverId: String, driverStat: Int): Completable {
        val data: Map<String, Int> = mapOf(Pair(FIREBASE_USER_DRIVER_STAT, driverStat + 1))
        return RxFirebaseDatabase.updateChildren(
            databaseReference.child(FIREBASE_USERS).child(driverId), data
        )
    }

    override fun fetchUpcomingJourneys(groupId: String): Flowable<MutableList<UpcomingJourney>> {
        return RxFirebaseDatabase.observeValueEvent(
            databaseReference.child(FIREBASE_GROUPS).child(groupId).child(
                FIREBASE_JOURNEYS
            ), DataSnapshotMapper.listOf(JourneyFirebase::class.java)
        ).map { firebaseModels ->
            val localModels: MutableList<UpcomingJourney> = mutableListOf()
            firebaseModels.forEach { localModels.add(it.toUpcomingJourney()) }
            localModels
        }
    }

    override fun fetchJourneyDetails(groupId: String, journeyId: String): Flowable<JourneyDetails> {
        return RxFirebaseDatabase.observeValueEvent(
            databaseReference.child(FIREBASE_GROUPS).child(groupId).child(FIREBASE_JOURNEYS)
                .child(journeyId), DataSnapshotMapper.of(JourneyFirebase::class.java)
        ).map { it.toJourneyDetails() }
    }

    override fun bookSeat(
        groupId: String,
        journeyId: String,
        userId: String,
        passengerStat: Int
    ): Completable {
        return doesPassengersNodeExist(groupId, journeyId)
            .flatMapCompletable { exists ->
                if (exists) {
                    addPassenger(groupId, journeyId, userId)
                } else {
                    addFirstPassenger(groupId, journeyId, userId)
                }
            }.andThen(updatePassengerStat(userId, passengerStat, true))
    }

    private fun updatePassengerStat(
        passengerId: String,
        passengerStat: Int,
        increment: Boolean
    ): Completable {
        val newPassengerStat = if (increment) passengerStat + 1 else passengerStat - 1
        val data: Map<String, Int> = mapOf(Pair(FIREBASE_USER_PASSENGER_STAT, newPassengerStat))
        return RxFirebaseDatabase.updateChildren(
            databaseReference.child(FIREBASE_USERS).child(passengerId), data
        )
    }

    override fun cancelBooking(
        groupId: String,
        journeyId: String,
        userId: String,
        passengerStat: Int
    ): Completable {
        return RxFirebaseDatabase.setValue(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS)
                .child(journeyId)
                .child(FIREBASE_PASSENGER_IDS)
                .child(userId), null
        ).andThen(updatePassengerStat(userId, passengerStat, false))
    }

    private fun addJourney(
        driverId: String,
        freeSeats: Int,
        timeStamp: Long,
        note: String?,
        startingLocationFirebase: LocationFirebase,
        destinationFirebase: LocationFirebase,
        groupId: String,
        driverAvatarUrl: String
    ): Completable {

        val key =
            databaseReference.child(FIREBASE_GROUPS).child(FIREBASE_JOURNEYS).push().key

        val journey = JourneyFirebase(
            key!!,
            driverId,
            freeSeats,
            emptyMap(),
            timeStamp,
            note,
            startingLocationFirebase,
            destinationFirebase,
            driverAvatarUrl
        )

        return RxFirebaseDatabase.setValue(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS)
                .child(key), journey
        )
    }

    private fun createJourneysNode(groupId: String): Completable {
        val data: Map<String, Boolean> = mapOf(Pair(FIREBASE_JOURNEYS, true))

        return RxFirebaseDatabase.updateChildren(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId), data
        )
    }

    private fun doesJourneysNodeExist(groupId: String): Single<Boolean> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS),
            DataSnapshot::exists
        ).defaultIfEmpty(false).toSingle()
    }

    private fun doesPassengersNodeExist(groupId: String, journeyId: String): Single<Boolean> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS)
                .child(journeyId)
                .child(FIREBASE_PASSENGER_IDS),
            DataSnapshot::exists
        ).defaultIfEmpty(false).toSingle()
    }

    private fun addPassenger(
        groupId: String,
        journeyId: String,
        userId: String
    ): Completable {
        return RxFirebaseDatabase.setValue(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS)
                .child(journeyId)
                .child(FIREBASE_PASSENGER_IDS)
                .child(userId),
            true
        )
    }

    private fun addFirstPassenger(
        groupId: String,
        journeyId: String,
        userId: String
    ): Completable {
        return RxFirebaseDatabase.setValue(
            databaseReference
                .child(FIREBASE_GROUPS)
                .child(groupId)
                .child(FIREBASE_JOURNEYS)
                .child(journeyId)
                .child(FIREBASE_PASSENGER_IDS)
                .child(userId),
            true
        )
    }
}