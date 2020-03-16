package com.example.macavity.data.repositories.group

import com.example.macavity.data.models.local.Group
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface GroupRepository {
    fun createGroup(creatorUserId: String): Completable
    fun joinGroup(groupId: String, userId: String): Completable
    fun fetchGroupFlowable(groupId: String): Flowable<Group>
    fun fetchGroupSingle(groupId: String): Single<Group>
}