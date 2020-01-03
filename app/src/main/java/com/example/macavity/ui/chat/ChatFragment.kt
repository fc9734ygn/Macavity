package com.example.macavity.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.macavity.R
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
