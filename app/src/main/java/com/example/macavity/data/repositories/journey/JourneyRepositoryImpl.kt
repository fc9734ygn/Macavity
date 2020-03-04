package com.example.macavity.data.repositories.journey

import com.example.macavity.data.models.firebase.JourneyFirebase
import com.example.macavity.data.models.firebase.LocationFirebase
import com.example.macavity.data.models.local.Location
import com.example.macavity.utils.FIREBASE_GROUPS
import com.example.macavity.utils.FIREBASE_JOURNEYS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
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
        destination: Location
    ): Completable {

        val startingLocationFirebase = LocationFirebase(
            startingLocation.placeId,
            startingLocation.address,
            startingLocation.longitude,
            startingLocation.latitude
        )

        val destinationFirebase = LocationFirebase(
            destination.placeId,
            destination.address,
            destination.longitude,
            destination.latitude
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
                        groupId
                    )
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
                                groupId
                            )
                        )
                }
            }
    }

    private fun addJourney(
        driverId: String,
        freeSeats: Int,
        timeStamp: Long,
        note: String?,
        startingLocationFirebase: LocationFirebase,
        destinationFirebase: LocationFirebase,
        groupId: String
    ): Completable {

        val key =
            databaseReference.child(FIREBASE_GROUPS).child(FIREBASE_JOURNEYS).push().key

        val journey = JourneyFirebase(
            key!!,
            driverId,
            freeSeats,
            emptyList(),
            timeStamp,
            note,
            startingLocationFirebase,
            destinationFirebase
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
}