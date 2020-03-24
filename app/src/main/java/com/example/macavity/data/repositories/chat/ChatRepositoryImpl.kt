package com.example.macavity.data.repositories.chat

import com.example.macavity.data.models.firebase.MessageFirebase
import com.example.macavity.data.models.local.Message
import com.example.macavity.utils.FIREBASE_CHAT
import com.example.macavity.utils.FIREBASE_GROUPS
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(val databaseReference: DatabaseReference) :
    ChatRepository {

    override fun sendMessage(
        content: String,
        timestamp: Long,
        userAvatarUrl: String,
        userId: String,
        username: String,
        groupId: String
    ): Completable {

        val chatReference =
            databaseReference.child(FIREBASE_GROUPS).child(groupId).child(FIREBASE_CHAT)
        val key = chatReference.push().key

        val message = MessageFirebase(
            key!!,
            content,
            timestamp,
            userId,
            username,
            userAvatarUrl
        )

        return RxFirebaseDatabase.setValue(
            chatReference.child(key),
            message
        )
    }

    override fun fetchChat(groupId: String): Flowable<MutableList<Message>> {

        val chatReference =
            databaseReference.child(FIREBASE_GROUPS).child(groupId).child(FIREBASE_CHAT)

        return RxFirebaseDatabase.observeValueEvent(
            chatReference,
            DataSnapshotMapper.listOf(MessageFirebase::class.java)
        ).map { remoteModels ->
            val localModels = mutableListOf<Message>()
            remoteModels.forEach {
                localModels.add(it.toMessage())
            }
            localModels
        }
    }
}