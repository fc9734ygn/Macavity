package com.example.macavity.data.models.firebase

import androidx.annotation.Keep
import com.example.macavity.data.models.local.Message
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class MessageFirebase(
    var id: String = "",
    var content: String = "",
    var timestamp: Long = 0,
    var userId: String = "",
    var username: String = "",
    var userAvatarUrl: String = ""
) {
    fun toMessage(): Message {
        return Message(id, content, timestamp, userId, username, userAvatarUrl)
    }
}