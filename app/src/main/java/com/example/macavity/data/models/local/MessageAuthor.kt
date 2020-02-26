package com.example.macavity.data.models.local

import com.stfalcon.chatkit.commons.models.IUser

data class MessageAuthor(val avatarUrl: String, val username: String, val userId: String) : IUser {

    override fun getAvatar(): String {
        return avatarUrl
    }

    override fun getName(): String {
        return username
    }

    override fun getId(): String {
        return userId
    }
}