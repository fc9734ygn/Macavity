package com.example.macavity.ui.chat

import com.example.macavity.data.models.local.MessageUI
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesListAdapter


class CustomMessagesListAdapter(senderId: String?, imageLoader: ImageLoader?) :
    MessagesListAdapter<MessageUI>(senderId, imageLoader) {

    //overriding this function because it does what I need (adds message if it's not present), but doesn't scroll to the bottom
    override fun upsert(message: MessageUI?) {
        if (!update(message)) {
            addToStart(message, true)
        }
    }
}