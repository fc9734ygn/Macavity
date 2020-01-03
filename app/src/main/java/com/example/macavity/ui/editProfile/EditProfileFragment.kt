package com.example.macavity.ui.editProfile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_edit_profile")
open class EditProfileFragment : BaseFragment() {

    private lateinit var vm: EditProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
