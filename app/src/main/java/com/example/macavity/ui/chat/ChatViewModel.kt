package com.example.macavity.ui.chat

import androidx.lifecycle.LiveDataReactiveStreams
import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.Message
import com.example.macavity.data.models.local.MessageUI
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.chat.ChatRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.base.BaseViewModel
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel() {


    private val userFlowable = sharedPreferencesManager
        .getCurrentUserId()
        .flatMapPublisher { userRepository.fetchUserFlowable(it) }

    private val chatFlowable = userFlowable.flatMap {
        currentUser = it
        chatRepository.fetchChat(it.groupId!!)
    }.map { list ->

        val sortedList = list.sortedBy { it.timestamp }
        val newList = mutableListOf<MessageUI>()

        sortedList.forEach {
            newList.add(it.toUIMessage())
        }

        newList
    }

    lateinit var currentUser: User

    val chat = LiveDataReactiveStreams.fromPublisher(chatFlowable)
    val user = LiveDataReactiveStreams.fromPublisher(userFlowable)

    fun sendMessage(content: String) {
        disposable.add(
            chatRepository.sendMessage(
                content,
                System.currentTimeMillis(),
                currentUser.avatarUrl,
                currentUser.id,
                currentUser.name,
                currentUser.groupId!!
            ).subscribe {
                //do nothing as we're observing the chat data
            }
        )
    }
}
