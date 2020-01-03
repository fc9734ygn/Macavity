package com.example.macavity.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.macavity.R
import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_profile")
open class ProfileFragment : BaseFragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
