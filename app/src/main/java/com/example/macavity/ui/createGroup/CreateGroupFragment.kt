package com.example.macavity.ui.createGroup

import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.home.HomeActivity_
import kotlinx.android.synthetic.main.fragment_create_group.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_create_group")
open class CreateGroupFragment : AuthFragment() {

    @AfterViews
    fun afterViews(){
        initToolbar()
    }

    private fun initToolbar(){
        toolbar.setTitle(getString(R.string.create_group_toolbar_title))
    }

    @Click(resName = ["create_group_button"])
    fun createGroup(){
        //todo: create group
        HomeActivity_.intent(requireContext()).start()
    }
}