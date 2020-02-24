package com.example.macavity.data.repositories.group

import com.example.macavity.data.models.Group
import com.example.macavity.utils.FIREBASE_GROUPS
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

    override fun createGroup(creatorUserId: String): Completable {

        val key = groupsReference.push().key

        val group = Group(key!!,creatorUserId, listOf(creatorUserId), emptyList())

        return RxFirebaseDatabase
            .setValue(groupsReference.child(key), group)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun joinGroup(groupId: String, userId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}