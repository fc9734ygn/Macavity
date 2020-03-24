package com.example.macavity.ui.chat

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.macavity.R
import com.example.macavity.data.models.local.MessageUI
import com.example.macavity.data.models.local.User
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
    private lateinit var messageAdapter: MessagesListAdapter<MessageUI>

    private val currentUserObserver = Observer<User> {
        initChat(it.id)
    }

    private val chatObserver = Observer<List<MessageUI>> { messages ->
        messages.forEach {
            messageAdapter.upsert(it, true)
        }
    }

    @AfterViews
    fun afterViews() {
        initViewModel()
        initToolbar()
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(ChatViewModel::class.java)
        vm.user.observe(this, currentUserObserver)
        vm.chat.observe(this, chatObserver)
    }

    private fun initChat(userId: String) {
        val imageLoader =
            ImageLoader { imageView, url, _ ->
                Glide.with(requireActivity()).load(url).circleCrop().into(imageView)
            }
        messageAdapter = CustomMessagesListAdapter(userId, imageLoader)
        messages_list.setAdapter(messageAdapter)
        message_input.setInputListener(messageInputListener)
    }

    private val messageInputListener = MessageInput.InputListener {
        vm.sendMessage(it.toString())
        true
    }


    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_arrow_back)
            .setTitle(getString(R.string.toolbar_title_chat))

        toolbar.startIconListener = { requireActivity().onBackPressed() }
    }
}
