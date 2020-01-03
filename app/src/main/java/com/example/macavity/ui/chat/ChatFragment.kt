package com.example.macavity.ui.chat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_chat")
open class ChatFragment : BaseFragment() {

    private lateinit var vm: ChatViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
