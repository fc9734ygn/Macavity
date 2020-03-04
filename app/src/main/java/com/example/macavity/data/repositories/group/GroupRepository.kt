package com.example.macavity.data.repositories.group

import io.reactivex.Completable

interface GroupRepository {
    fun createGroup(creatorUserId: String): Completable
    fun joinGroup(groupId: String, userId: String): Completable
}