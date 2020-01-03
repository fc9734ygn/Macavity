package com.example.macavity.ui.settings

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import com.example.macavity.ui.base.BaseFragment
import org.androidannotations.annotations.EFragment

@EFragment(resName = "fragment_settings")
open class SettingsFragment : BaseFragment() {

    private lateinit var vm: SettingsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
