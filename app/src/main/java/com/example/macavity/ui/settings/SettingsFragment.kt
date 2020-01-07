package com.example.macavity.ui.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import com.example.macavity.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_settings")
open class SettingsFragment : BaseFragment() {

    private lateinit var vm: SettingsViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setStartIcon(R.drawable.ic_menu)
            .setTitle(getString(R.string.toolbar_title_settings))

        toolbar.startIconListener = { openDrawer() }
    }

}