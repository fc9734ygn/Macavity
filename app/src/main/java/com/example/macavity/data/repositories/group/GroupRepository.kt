package com.example.macavity.data.repositories.group

import io.reactivex.Completable
import io.reactivex.Single

interface GroupRepository {
    fun createGroup(creatorUserId: String): Completable
    fun joinGroup(groupId: String, userId: String): Completable
}