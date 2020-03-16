package com.example.macavity.data.repositories.group

import com.example.macavity.data.models.firebase.GroupFirebase
import com.example.macavity.data.models.firebase.UserFirebase
import com.example.macavity.data.models.local.Group
import com.example.macavity.utils.FIREBASE_GROUPS
import com.example.macavity.utils.FIREBASE_GROUP_ID
import com.example.macavity.utils.FIREBASE_GROUP_MEMBERS
import com.example.macavity.utils.FIREBASE_USERS
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(databaseReference: DatabaseReference) :
    GroupRepository {

    private val groupsReference: DatabaseReference = databaseReference.child(FIREBASE_GROUPS)
    private val usersReference: DatabaseReference = databaseReference.child(FIREBASE_USERS)

    override fun createGroup(creatorUserId: String): Completable {

        val key = groupsReference.push().key
        val membersMap = hashMapOf(creatorUserId to true)

        val group = GroupFirebase(
            key!!,
            creatorUserId,
            membersMap,
            emptyMap(),
            emptyMap()
        )

        return RxFirebaseDatabase
            .setValue(groupsReference.child(key), group)
            .andThen(addGroupIdToUser(key, creatorUserId))
    }

    override fun joinGroup(groupId: String, userId: String): Completable {
        return RxFirebaseDatabase.setValue(
            groupsReference
                .child(groupId)
                .child(FIREBASE_GROUP_MEMBERS)
                .child(userId),
            true
        ).andThen(addGroupIdToUser(groupId, userId))
    }

    private fun addGroupIdToUser(groupId: String, userId: String): Completable {
        val map: Map<String, String> = mapOf(Pair(FIREBASE_GROUP_ID, groupId))
        return RxFirebaseDatabase.updateChildren(usersReference.child(userId), map)
    }


    override fun fetchGroupFlowable(groupId: String): Flowable<Group> {
        return RxFirebaseDatabase.observeValueEvent(
            groupsReference.child(groupId), DataSnapshotMapper.of(
                GroupFirebase::class.java
            )
        ).map { it.toGroup() }
    }

    override fun fetchGroupSingle(groupId: String): Single<Group> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            groupsReference.child(groupId), DataSnapshotMapper.of(
                GroupFirebase::class.java
            )
        ).map { it.toGroup() }.toSingle()
    }
}