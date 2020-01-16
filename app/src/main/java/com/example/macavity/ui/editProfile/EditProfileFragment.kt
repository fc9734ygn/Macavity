package com.example.macavity.ui.editProfile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.macavity.R

import com.example.macavity.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_edit_profile")
open class EditProfileFragment : BaseFragment() {

    private lateinit var vm: EditProfileViewModel

    @AfterViews
    fun afterViews() {
        vm = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        initToolbar()
        toggleBottomNav(false)
    }

    private fun initToolbar() {
        toolbar.setTitle(getString(R.string.profile_edit_toolbar_title))
            .setStartIcon(R.drawable.ic_arrow_back)
            .setEndIcon(R.drawable.ic_save)

        toolbar.startIconListener = { activity!!.onBackPressed() }
        //TODO: save changes
        toolbar.endIconListener = {}
    }

}
