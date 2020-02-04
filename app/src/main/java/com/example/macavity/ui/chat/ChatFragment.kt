package com.example.macavity.ui.chat

import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.chat.Author
import com.example.macavity.data.models.chat.Message
import com.example.macavity.ui.base.HomeFragment
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_map.toolbar
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment


@EFragment(resName = "fragment_chat")
open class ChatFragment : HomeFragment() {

    private lateinit var vm: ChatViewModel
    private lateinit var messageAdapter: MessagesListAdapter<Message>
    //TODO: remove dummy data
    private val dummyAvatarUrl =
        "https://66.media.tumblr.com/4c69fcb24a6d09010e6f818b31eba7c5/tumblr_po8044mLw21truxr0_540.jpg"

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        initToolbar()
        initChat()
    }

    private fun initChat() {

        //TODO: use real data
        val dummySenderId = "23f34f"
        val imageLoader =
            ImageLoader { imageView, url, _ -> Glide.with(requireActivity()).load(url).centerCrop().into(imageView) }

        messageAdapter = MessagesListAdapter(dummySenderId, imageLoader)
        messages_list.setAdapter(messageAdapter)
        messageAdapter.addToStart(
            Message(
                "1",
                1578767340,
                Author(dummyAvatarUrl, "John", "123123"),
                "yo"
            ), false
        )
        messageAdapter.addToStart(
            Message(
                "2",
                1578767440,
                Author(dummyAvatarUrl, "Alan", "23f34f"),
                "sup"
            ), false
        )
        messageAdapter.addToStart(
            Message(
                "3",
                1578767740,
                Author(dummyAvatarUrl, "Alan", "23f34f"),
                "need a ride?"
            ), false
        )
        messageAdapter.addToStart(
            Message(
                "4",
                1578767940,
                Author(dummyAvatarUrl, "John", "123123"),
                "y bruv"
            ), false
        )
        messageAdapter.addToStart(
            Message(
                "5",
                1578768340,
                Author(dummyAvatarUrl, "Alan", "23f34f"),
                "gimme 5"
            ), false
        )
        messageAdapter.addToStart(
            Message(
                "6",
                1578769340,
                Author(dummyAvatarUrl, "John", "123123"),
                "cool"
            ), false
        )

        message_input.setInputListener(messageInputListener)
    }

    private val messageInputListener = MessageInput.InputListener {
        //TODO: send message to server and delete this placeholder
        val message = Message(
            "1123123",
            System.currentTimeMillis(),
            Author(dummyAvatarUrl, "Alan", "23f34f"),
            it.toString()
        )
        messageAdapter.addToStart(message, true)
        true
    }


    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_chat))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }
}
