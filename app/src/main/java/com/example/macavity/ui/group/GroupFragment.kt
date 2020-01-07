package com.example.macavity.ui.group

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_group")
open class GroupFragment : BaseFragment() {

    private lateinit var vm: GroupViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_group))

        toolbar.startIconListener = { openDrawer() }
    }
}
