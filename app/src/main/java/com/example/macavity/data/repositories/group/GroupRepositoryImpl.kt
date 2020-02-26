package com.example.macavity.data.repositories.group

import com.example.macavity.data.models.local.Group
import com.example.macavity.utils.FIREBASE_GROUPS
import com.example.macavity.utils.FIREBASE_GROUP_ID
import com.example.macavity.utils.FIREBASE_USERS
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(databaseReference: DatabaseReference) :
    GroupRepository {

    private val groupsReference: DatabaseReference = databaseReference.child(FIREBASE_GROUPS)
    private val usersReference: DatabaseReference = databaseReference.child(FIREBASE_USERS)

    override fun createGroup(creatorUserId: String): Completable {

        val key = groupsReference.push().key

        val group = Group(
            key!!,
            creatorUserId,
            listOf(creatorUserId),
            emptyList(),
            emptyList()
        )

        return RxFirebaseDatabase
            .setValue(groupsReference.child(key), group)
            .andThen(addGroupIdToUser(key, creatorUserId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun joinGroup(groupId: String, userId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun addGroupIdToUser(groupId: String, userId: String): Completable {
        val map: Map<String, String> = mapOf(Pair(FIREBASE_GROUP_ID, groupId))
        return RxFirebaseDatabase.updateChildren(usersReference.child(userId), map)
    }
}