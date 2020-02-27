package com.example.macavity.ui.createGroup

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.macavity.R
import com.example.macavity.ui.base.AuthFragment
import com.example.macavity.ui.home.HomeActivity_
import kotlinx.android.synthetic.main.fragment_create_group.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_create_group")
open class CreateGroupFragment : AuthFragment() {

    lateinit var vm: CreateGroupViewModel

    private val groupCreationObserver = Observer<Boolean> {
        if (it) {
            HomeActivity_.intent(requireContext()).start()
        }
    }

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this, viewModelFactory).get(CreateGroupViewModel::class.java)
        vm.userIsInGroup.observe(this, groupCreationObserver)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.create_group_toolbar_title))
    }

    @Click(resName = ["create_group_button"])
    fun createGroup() {
        vm.createGroup()
    }

    @Click(resName = ["join_button"])
    fun joinGroup() {
        if (invitation_code.text.isNullOrBlank()) {
            invitation_code.background =
                resources.getDrawable(R.drawable.background_grey_rounded_red_border, null)
        } else {
            vm.joinGroup(invitation_code.text.toString())
        }
    }

    @Click(resName = ["invitation_code"])
    fun onInvitationCodeEnter(){
        invitation_code.background = resources.getDrawable(R.drawable.background_grey_rounded, null)
    }
}