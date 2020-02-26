package com.example.macavity.ui.chat

import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.MessageAuthor
import com.example.macavity.data.models.local.MessageUI
import com.example.macavity.ui.base.HomeFragment
import com.example.macavity.utils.millisecondsToSeconds
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
    private lateinit var messageAdapter: MessagesListAdapter<MessageUI>
    //TODO: remove dummy data
    private val dummyAvatarUrl =
        "https://media.gettyimages.com/photos/businessman-wearing-eyeglasses-picture-id825083358?b=1&k=6&m=825083358&s=612x612&w=0&h=SV2xnROuodWTh-sXycr-TULWi-bdlwBDXJkcfCz2lLc="

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        initToolbar()
        initChat()
    }

    private fun initChat() {

        //TODO: use real data
        val dummySenderId = "23f34f"
        val imageLoader =
            ImageLoader { imageView, url, _ -> Glide.with(requireActivity()).load(url).circleCrop().into(imageView) }

        messageAdapter = MessagesListAdapter(dummySenderId, imageLoader)
        messages_list.setAdapter(messageAdapter)
        messageAdapter.addToStart(
            MessageUI(
                "1",
                millisecondsToSeconds(1581004042000),
                MessageAuthor(
                    dummyAvatarUrl,
                    "John",
                    "123123"
                ),
                "I need to stop by the post office tomorrow in the morning"
            ), false
        )
        messageAdapter.addToStart(
            MessageUI(
                "3",
                millisecondsToSeconds(1581004042000),
                MessageAuthor(
                    dummyAvatarUrl,
                    "Alan",
                    "23f34f"
                ),
                "How long should it take?"
            ), false
        )
        messageAdapter.addToStart(
            MessageUI(
                "4",
                millisecondsToSeconds(1581004042000),
                MessageAuthor(
                    dummyAvatarUrl,
                    "John",
                    "123123"
                ),
                "Just a few minutes"
            ), false
        )
        messageAdapter.addToStart(
            MessageUI(
                "5",
                millisecondsToSeconds(1581004042000),
                MessageAuthor(
                    dummyAvatarUrl,
                    "Alan",
                    "23f34f"
                ),
                "Alright, maybe we can leave 5 minutes earlier then?"
            ), false
        )
        messageAdapter.addToStart(
            MessageUI(
                "6",
                millisecondsToSeconds(1581004042000),
                MessageAuthor(
                    dummyAvatarUrl,
                    "John",
                    "123123"
                ),
                "Yeah, sure. See you tomorrow"
            ), false
        )

        message_input.setInputListener(messageInputListener)
    }

    private val messageInputListener = MessageInput.InputListener {
        //TODO: send message to server and delete this placeholder
        val message = MessageUI(
            "1123123",
            millisecondsToSeconds(System.currentTimeMillis()),
            MessageAuthor(
                dummyAvatarUrl,
                "Alan",
                "23f34f"
            ),
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
