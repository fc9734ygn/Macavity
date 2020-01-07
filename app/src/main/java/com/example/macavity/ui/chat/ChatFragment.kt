package com.example.macavity.ui.chat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_chat")
open class ChatFragment : BaseFragment() {

    private lateinit var vm: ChatViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_chat))

        toolbar.startIconListener = { openDrawer() }
    }
}
