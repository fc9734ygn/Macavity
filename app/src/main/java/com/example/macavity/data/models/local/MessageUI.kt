package com.example.macavity.data.models.local

import com.example.macavity.utils.millisecondsToDate
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

data class MessageUI(
    val messageId: String,
    val timestamp: Long,
    val user: MessageAuthor,
    val messageText: String
) : IMessage {

    override fun getId(): String {
        return messageId
    }

    override fun getCreatedAt(): Date {
        return millisecondsToDate(timestamp)
    }

    override fun getUser(): IUser {
        return user
    }

    override fun getText(): String {
        return messageText
    }
}