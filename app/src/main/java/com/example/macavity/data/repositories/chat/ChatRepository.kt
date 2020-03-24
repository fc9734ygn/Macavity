package com.example.macavity.data.repositories.chat

import com.example.macavity.data.models.local.Message
import io.reactivex.Completable
import io.reactivex.Flowable

interface ChatRepository {

    fun sendMessage(
        content: String,
        timestamp: Long,
        userAvatarUrl: String,
        userId: String,
        username: String,
        groupId: String
    ): Completable

    fun fetchChat(groupId: String): Flowable<MutableList<Message>>
}