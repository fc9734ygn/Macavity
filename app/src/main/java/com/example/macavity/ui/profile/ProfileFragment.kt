package com.example.macavity.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.ui.calendar.CalendarViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_profile")
open class ProfileFragment : BaseFragment() {

    private lateinit var vm: ProfileViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setEndIcon(R.drawable.ic_edit)
            .setTitle(getString(R.string.toolbar_title_profile))

        toolbar.startIconListener = { openDrawer() }
        //TODO: disable if not your profile
        toolbar.endIconListener = { goToEditProfile() }
    }

    private fun goToEditProfile() {
        findNavController().navigate(R.id.action_profileFragment__to_editProfileFragment_)
    }
}
