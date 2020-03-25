package com.example.macavity.data.repositories.group

import com.example.macavity.data.models.firebase.GroupFirebase
import com.example.macavity.data.models.firebase.MessageFirebase
import com.example.macavity.data.models.local.Group
import com.example.macavity.utils.*
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

        val groupId = groupsReference.push().key
        val membersMap = hashMapOf(creatorUserId to true)

        val group = GroupFirebase(
            groupId!!,
            creatorUserId,
            membersMap,
            emptyMap()
        )

        return RxFirebaseDatabase
            .setValue(groupsReference.child(groupId), group)
            .andThen(addGroupIdToUser(groupId, creatorUserId))
            .andThen(createChat(groupId))
    }

    private fun createChat(groupId: String): Completable {
        val key = groupsReference.child(FIREBASE_CHAT).push().key

        //todo: use logo image
        val firstMessage = MessageFirebase(
            "dummyId",
            "Hi there, here you can chat with your group members! \uD83D\uDE09 ",
            System.currentTimeMillis(),
            "dummyUserId",
            "Friendly Cat",
            "https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
        )

        return RxFirebaseDatabase.setValue(
            groupsReference.child(groupId).child(FIREBASE_CHAT).child(key!!),
            firstMessage
        )
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