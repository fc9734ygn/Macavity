package com.example.macavity.data.models.local

import com.example.macavity.utils.millisecondsToSeconds

data class Message(
    val id: String,
    val content: String,
    val timestamp: Long,
    val userId: String,
    val username: String,
    val userAvatarUrl: String
) {
    fun toUIMessage(): MessageUI {
        return MessageUI(
            id,
            millisecondsToSeconds(timestamp),
            MessageAuthor(userAvatarUrl, username, userId),
            content
        )
    }
}